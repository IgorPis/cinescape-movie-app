package com.movie.services;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.movie.repositories.GenreRepository;
import com.movie.repositories.LanguageRepository;
import com.movie.repositories.MovieRepository;

import model.Genre;
import model.Language;
import model.Movie;

@Service
public class MovieService {
	
    @Value("${file.upload-dir}")
    private String movieUploadDir;

    @Autowired
    private MovieRepository movieRepo;

    @Autowired
    private LanguageRepository languageRepo;

    @Autowired
    private GenreRepository genreRepo;

    public List<Movie> getAllMovies() {
        return movieRepo.findAll();
    }

    public Movie getMovieByTitle(String title) {
        return movieRepo.findByTitle(title).orElseThrow(() -> new RuntimeException("Movie not found"));
    }
    
    public Movie findMovieById(int idMovie) {
    	Movie movie = movieRepo.findById(idMovie).get();
    	return movie;
    }
    
    public String convertMinutesToHours(int totalMinutes) {
        if (totalMinutes < 60) {
            return String.format("%dm", totalMinutes);
        } else {
            int hours = totalMinutes / 60;
            int minutes = totalMinutes % 60;
            return String.format("%dh %dm", hours, minutes);
        }
    }
    
    public String saveMovieWithImage(MultipartFile posterPath, String title, int duration, String description, int releaseYear, String trailerKey, int idGenre, int idLang) {
        if (posterPath.isEmpty()) {
            return "File is empty";
        }

        try {
            String fileName = posterPath.getOriginalFilename();
            File directory = new File(movieUploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File serverFile = new File(directory.getAbsolutePath() + File.separator + fileName);
            posterPath.transferTo(serverFile);

            String imagePath = "/CineScape/imgMovies/" + fileName;
            Genre genre = genreRepo.findById(idGenre).orElseThrow(() -> new RuntimeException("Genre not found"));
            Language language = languageRepo.findById(idLang).orElseThrow(() -> new RuntimeException("Language not found"));
            Movie movie = new Movie();
            movie.setTitle(title);
            movie.setDescription(description);
            movie.setDuration(duration);
            movie.setReleaseYear(releaseYear);
            movie.setTrailerKey(trailerKey);
            movie.setGenre(genre);
            movie.setLanguage(language);
            movie.setPosterPath(imagePath);
            movieRepo.save(movie);

            return "Movie saved successfully!";
        } catch (IOException e) {
            return "Failed to save Movie";
        }
    }


    public Movie updateMovieById(int idMovie, String title, int duration, String description, int releaseYear, String trailerKey, MultipartFile posterPath, int idGenre, int idLang) throws IOException {
        Movie existingMovie = movieRepo.findById(idMovie).orElseThrow(() -> new RuntimeException("Movie not found"));

        if (posterPath != null && !posterPath.isEmpty()) {
            String fileName = posterPath.getOriginalFilename();
            File directory = new File(movieUploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File serverFile = new File(directory.getAbsolutePath() + File.separator + fileName);
            posterPath.transferTo(serverFile);

            String imagePath = "/CineScape/imgMovies/" + fileName;
            existingMovie.setPosterPath(imagePath);
        }

        Genre genre = genreRepo.findById(idGenre).orElseThrow(() -> new RuntimeException("Genre not found"));
        Language language = languageRepo.findById(idLang).orElseThrow(() -> new RuntimeException("Language not found"));

        existingMovie.setTitle(title);
        existingMovie.setDescription(description);
        existingMovie.setDuration(duration);
        existingMovie.setReleaseYear(releaseYear);
        existingMovie.setTrailerKey(trailerKey);
        existingMovie.setGenre(genre);
        existingMovie.setLanguage(language);

        movieRepo.save(existingMovie);

        return existingMovie;
    }



    public String deleteMovieById(int idMovie) {
        try {
			Movie movie = movieRepo.findById(idMovie).orElseThrow(() -> new RuntimeException("Movie not found"));
			movieRepo.delete(movie);
			System.out.println("Deleted movie with the id: " + idMovie + ", "+ movie.getTitle());
			return "Deleted movie with the id: " + idMovie + ", "+ movie.getTitle();
		} catch (RuntimeException e) {
			 e.printStackTrace();
			 return "Failed to delete movie";
		}
    }
    
    public void updateMovieViews(Movie movie) {
    	int newViews = movie.getViews() + 1;
    	movie.setViews(newViews);
    	movieRepo.save(movie);
    }

    public List<Movie> filterMoviesByLanguage(int idLang) {
        Language language = languageRepo.findById(idLang).orElseThrow(() -> new RuntimeException("Language not found"));
        return movieRepo.findByLanguage(language);
    }

    public List<Movie> filterMoviesByGenre(int idGenre) {
        Genre genre = genreRepo.findById(idGenre).orElseThrow(() -> new RuntimeException("Genre not found"));
        return movieRepo.findByGenre(genre);
    }
    
    public List<Genre> getGenres() {
    	return genreRepo.findAll();
    }
    
	public List<Language> getLang() {
		return languageRepo.findAll();
	}
	
	
}
