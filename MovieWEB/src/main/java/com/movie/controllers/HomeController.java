package com.movie.controllers;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.movie.repositories.MovieRepository;
import com.movie.services.MovieService;

import jakarta.servlet.http.HttpServletRequest;
import model.Genre;
import model.Language;
import model.Movie;

@Controller
@RequestMapping("/home/")
public class HomeController {
	
	@Autowired
	private MovieService movieService;
	
	@Autowired
	MovieRepository mr;
	
    
    @GetMapping("searchMovies")
    @ResponseBody
    public List<String> searchMovies(@RequestParam("query") String query) {
        List<Movie> allMovies = movieService.getAllMovies();

        return allMovies.stream()
            .map(Movie::getTitle)
            .filter(title -> title.toLowerCase().contains(query.toLowerCase()))
            .sorted((title1, title2) -> {
                if (title1.toLowerCase().startsWith(query.toLowerCase()) && !title2.toLowerCase().startsWith(query.toLowerCase())) {
                    return -1;
                } else if (!title1.toLowerCase().startsWith(query.toLowerCase()) && title2.toLowerCase().startsWith(query.toLowerCase())) {
                    return 1;
                } else {
                    return title1.compareToIgnoreCase(title2);
                }
            })
            .collect(Collectors.toList());
    }
    
    
    @GetMapping("getByTitle")
    public String getMovieByTitle(@RequestParam String title, HttpServletRequest request) {
        try {
            // Trim and set the search query in the request to display in the view
            title = title.trim();
            request.setAttribute("searchQuery", title);

            // Try to find the movie by title
            Movie movie = movieService.getMovieByTitle(title);
            request.setAttribute("movieByTitle", movie);
        } catch (Exception e) {
            // Log the error
            e.printStackTrace();

            // Set a message and the search query
            String msg = "Movie not found";
            request.setAttribute("MovieNotFoundMsg", msg);
            request.setAttribute("searchQuery", title);

            // Fetch all movies to display when the search fails
            List<Movie> allMovies = movieService.getAllMovies();
            request.setAttribute("allMovies", allMovies);
        }
        return "search";
    }
    
   
    //not used yet
    @GetMapping("getGenres")
    public String getGenres(HttpServletRequest request) {
    	List<Genre> genres = movieService.getGenres();
    	request.setAttribute("genres", genres);
    	return "";
    }
    
    
    @GetMapping("getAllMovies")
    public String getMoviesByGenreAndSort(@RequestParam(value = "idGenre", required = false) Integer idGenre,
                                          @RequestParam(value = "sortOrder", required = false) String sortOrder,
                                          HttpServletRequest request) {
        List<Movie> movies;

        if (idGenre != null && idGenre > 0) {
            movies = movieService.filterMoviesByGenre(idGenre);
        } else {
            movies = movieService.getAllMovies();
        }

        if ("A-Z".equals(sortOrder)) {
            movies.sort(Comparator.comparing(Movie::getTitle));
        } else if ("Z-A".equals(sortOrder)) {
            movies.sort(Comparator.comparing(Movie::getTitle).reversed());
        }

        List<Genre> genres = movieService.getGenres();
        request.setAttribute("allmovies", movies);
        request.getSession().setAttribute("allgenres", genres);

        return "browse-gen";
    }


	@GetMapping("getLangs")
	public String getLang(HttpServletRequest request) {
		List<Language> langs = movieService.getLang();
		List<Movie> englishMovies = movieService.filterMoviesByLanguage(1);
		request.setAttribute("englishMovies", englishMovies);
		request.getSession().setAttribute("langs", langs);
		return "browse-lan";
	}
	
	
	@GetMapping("moviesByLanguage")
	public String getMovieByLang(@RequestParam int idLang, @RequestParam(required = false) String sortOrder, HttpServletRequest request) {
	    List<Movie> movies = movieService.filterMoviesByLanguage(idLang);

	    if (sortOrder != null) {
	        if (sortOrder.equals("A-Z")) {
	            movies.sort(Comparator.comparing(Movie::getTitle));
	        } else if (sortOrder.equals("Z-A")) {
	            movies.sort(Comparator.comparing(Movie::getTitle).reversed());
	        }
	    }

	    request.setAttribute("moviesByLanguage", movies);
	    return "browse-lan";
	}

	
    @GetMapping("mostPopular")
    public String getTop10Movies(HttpServletRequest request) {
        List<Movie> top10Movies = mr.findTop10ByViews(PageRequest.of(0, 10));
        request.setAttribute("mostPopular", top10Movies);
        request.setAttribute("mostPopularTitle", "Top 10 Most Popular Movies");
        return "browse-gen";
    }
    
//  old method for searching movies, this one sorts them out by id in database
//  @GetMapping("searchMovies")
//  @ResponseBody
//  public List<String> searchMovies(@RequestParam("query") String query) {
//      List<Movie> allMovies = movieService.getAllMovies();
//      return allMovies.stream()
//              .map(Movie::getTitle)
//              .filter(title -> title.toLowerCase().contains(query.toLowerCase()))
//              .collect(Collectors.toList());
//  }
	
}
