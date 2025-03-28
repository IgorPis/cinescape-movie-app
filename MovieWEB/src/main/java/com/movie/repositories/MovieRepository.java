package com.movie.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import model.Genre;
import model.Language;
import model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
	
	Optional<Movie> findByTitle(String title);
	
	List<Movie> findByLanguage(Language lang);
	
	List<Movie> findByGenre(Genre genre);
	
    @Query("SELECT m FROM Movie m ORDER BY m.views DESC")
    List<Movie> findTop10ByViews(Pageable pageable);
    
}
