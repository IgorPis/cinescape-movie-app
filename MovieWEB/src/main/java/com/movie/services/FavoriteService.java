package com.movie.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.movie.repositories.FavoriteRepository;
import com.movie.repositories.MovieRepository;
import com.movie.repositories.UserRepository;

import model.AppUser;
import model.Favorite;
import model.Movie;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepo;

    @Autowired
    private MovieRepository movieRepo;

    @Autowired
    private UserRepository userRepo;

    @Transactional
    public String addFavorite(int idUser, int idMovie) {
        AppUser user = userRepo.findById(idUser).orElseThrow(() -> new RuntimeException("User not found"));
        Movie movie = movieRepo.findById(idMovie).orElseThrow(() -> new RuntimeException("Movie not found"));
        Favorite fav = new Favorite();
        fav.setAppUser(user);
        fav.setMovie(movie);
        favoriteRepo.save(fav);
        return "Movie added to favorites with the title: " + movie.getTitle();
    }

    public List<Movie> showFav(int idUser) {
        AppUser user = userRepo.findById(idUser).orElseThrow(() -> new RuntimeException("User not found"));
        List<Favorite> favs = favoriteRepo.findByAppUser(user);
        return favs.stream().map(Favorite::getMovie).collect(Collectors.toList());
    }

    @Transactional
    public String removeMovieFromFavorites(int idUser, int idMovie) {
        AppUser user = userRepo.findById(idUser).orElseThrow(() -> new RuntimeException("User not found"));
        Movie movie = movieRepo.findById(idMovie).orElseThrow(() -> new RuntimeException("Movie not found"));
        Favorite favorite = favoriteRepo.findByAppUserAndMovie(user, movie);
        if (favorite != null) {
            favoriteRepo.delete(favorite);
            return "Removed from favorites: " + movie.getTitle();
        }
        return "Removing failed..";
    }
    
    public boolean isFavoriteMovie(AppUser appUser, Movie movie) {
        return favoriteRepo.existsByAppUserAndMovie(appUser, movie);
    }

}
