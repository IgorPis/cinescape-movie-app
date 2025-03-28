package com.movie.controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.movie.repositories.GenreRepository;
import com.movie.repositories.LanguageRepository;
import com.movie.repositories.MovieRepository;

import jakarta.servlet.http.HttpServletRequest;
import model.Genre;
import model.Language;
import model.Movie;

@Controller
@RequestMapping("/obican/")
public class ObicanControler {
	
	@Autowired
	MovieRepository mr;
	
	@Autowired
	LanguageRepository lr;
	
	@Autowired
	GenreRepository gr;
	
    @GetMapping("pocetna")
    public String getPocetna (){
       
        return "index";
    }
	
	@GetMapping("findMovie")
	public String findMovie(HttpServletRequest request, String title) {
		Movie m = mr.findByTitle(title).orElseThrow(() -> new RuntimeException("Movie not found"));
		request.setAttribute("movie", m);
		return "FindMovie";
	}
	
	@GetMapping("getLang")
	public String getLang(HttpServletRequest request) {
		List<Language> langs = lr.findAll();
		request.getSession().setAttribute("langs", langs);
		return "FilterLang";
	}
	
	@GetMapping("getML")
	public String getML(int idLang, HttpServletRequest request) {
		Language l = lr.findById(idLang).get();
		List<Movie> m = mr.findByLanguage(l);
		request.setAttribute("movies", m);
		return "FilterLang";
	}
	
	@GetMapping("getGenres")
	public String getGenre(HttpServletRequest request) {
		List<Genre> genres = gr.findAll();
		request.getSession().setAttribute("genres", genres);
		return "FilterGenre";
	}
	
	@GetMapping("getMG")
	public String getMG(int idGenre, HttpServletRequest request) {
		Genre g = gr.findById(idGenre).get();
		List<Movie> m = mr.findByGenre(g);
		request.setAttribute("movies", m);
		return "FilterGenre";
	}
	
    @GetMapping("top10Movies")
    public String getTop10Movies(HttpServletRequest request) {
        List<Movie> top10Movies = mr.findTop10ByViews(PageRequest.of(0, 10));
        request.setAttribute("top10Movies", top10Movies);
        return "MostPopular"; 
    }
	
	
	  @PostMapping("saveMovieImg")
	    public String saveMovieImg(@RequestParam("posterPath") MultipartFile posterPath, String title,
	    		int idGenre, int idLang, HttpServletRequest request) {
	        if (posterPath.isEmpty()) {
	            return "File is empty";
	        }

	        try {
	            String fileName = posterPath.getOriginalFilename();
	            String uploadDir = "src/main/resources/static/imgMovies/";
	            File directory = new File(uploadDir);
	            if (!directory.exists()) {
	                directory.mkdirs();
	            }
	            File serverFile = new File(directory.getAbsolutePath() + File.separator + fileName);
	            posterPath.transferTo(serverFile);
	            
	            String imagePath = "/Movie/imgMovies/" + fileName;
	            Genre g = gr.findById(idGenre).get();
	            Language l = lr.findById(idLang).get();
	            Movie movie = new Movie();
	            movie.setTitle(title);
	            movie.setGenre(g);
	            movie.setLanguage(l);
	            movie.setPosterPath(imagePath);
	            mr.save(movie);
	            String msg = "File uploaded successfully!";
	            request.setAttribute("msg", msg);
	            
	            return "SaveMovieImg.jsp";
	        } catch (IOException e) {
	        	String msg = "Failed to upload file";
	        	request.setAttribute("msg", msg);
	        	return "SaveMovieImg";
	        }
	    }
	
}
