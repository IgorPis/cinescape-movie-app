package com.movie.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.AppUser;
import model.Favorite;
import model.Movie;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer>{
	
	List<Favorite> findByAppUser(AppUser appUser);
	
	Favorite findByAppUserAndMovie(AppUser appUser, Movie movie);

	boolean existsByAppUserAndMovie(AppUser appUser, Movie movie);
}
