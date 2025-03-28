package com.movie.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.movie.security.UserDetailsImpl;
import com.movie.services.FavoriteService;

import jakarta.servlet.http.HttpServletRequest;
import model.Movie;

@Controller
@RequestMapping("/my-list/")
public class FavoriteController {
	
	@Autowired
	private FavoriteService favoriteService;

	@PostMapping("addToFavorites/{idMovie}")
	public String addFavorite(HttpServletRequest request, @PathVariable int idMovie) {
		UserDetailsImpl userDetails = (UserDetailsImpl) request.getSession().getAttribute("loggedUser");
		int idUser = userDetails.getIdUser();
		favoriteService.addFavorite(idUser, idMovie);
		
	    return "redirect:/user/getMovieDetails?idMovie=" + idMovie;
	}

	@GetMapping("showFavorites")
	public String showFav(HttpServletRequest request) {
		UserDetailsImpl userDetails = (UserDetailsImpl) request.getSession().getAttribute("loggedUser");
		int idUser = userDetails.getIdUser();
		List<Movie> movies = favoriteService.showFav(idUser);
		request.setAttribute("favoritesOfUser", movies);
		return "my-list";
	}

	@PostMapping("removeFromFavorites/{idMovie}")
	public String removeMovieFromFavorites(HttpServletRequest request, @PathVariable int idMovie) {
		UserDetailsImpl userDetails = (UserDetailsImpl) request.getSession().getAttribute("loggedUser");
		int idUser = userDetails.getIdUser();
		favoriteService.removeMovieFromFavorites(idUser, idMovie);
		
		return "redirect:/user/getMovieDetails?idMovie=" + idMovie;
	}
	
}
