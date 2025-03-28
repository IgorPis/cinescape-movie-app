
package com.movie.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.movie.security.UserDetailsImpl;
import com.movie.services.FavoriteService;
import com.movie.services.MovieService;
import com.movie.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import model.AppUser;
import model.Comment;
import model.Genre;
import model.Movie;

@Controller
@RequestMapping("/user/")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private MovieService movieService;
	
	@Autowired
	private FavoriteService favoriteService;

	@GetMapping("getMovieDetails")
    public String getMovieById(@RequestParam int idMovie, HttpServletRequest request) {
    	try {
			Movie movie = movieService.findMovieById(idMovie);
			String formattedDuration = movieService.convertMinutesToHours(movie.getDuration());
			List<Comment> comments = userService.getCommentsOfMovie(movie);

			movieService.updateMovieViews(movie);
			Genre genre = movie.getGenre();
			int idGenre = genre.getIdGenre();
			List<Movie> moviesByGenre = movieService.filterMoviesByGenre(idGenre);
			moviesByGenre.remove(movie);
			if (moviesByGenre.size() >= 6) {
			    moviesByGenre = moviesByGenre.subList(0, 6);
			} else if (moviesByGenre.size() >= 5) {
				moviesByGenre = moviesByGenre.subList(0, 5);
			} else if (moviesByGenre.size() >= 4) {
				moviesByGenre = moviesByGenre.subList(0, 4);
			} else {
				moviesByGenre = moviesByGenre.subList(0, 3);
			}
			request.setAttribute("relatedMovies", moviesByGenre);
			request.setAttribute("movieById", movie);
			request.setAttribute("duration", formattedDuration);
			if(comments.isEmpty()) {
				String messageComments = "No comments yet. Be first to leave a comment.";
				request.setAttribute("msgComment", messageComments);
			} else {
				request.setAttribute("comments", comments);
			}
			
            UserDetailsImpl userDetails = (UserDetailsImpl) request.getSession().getAttribute("loggedUser");
            AppUser appUser = userDetails.getAppUser();
            boolean isFavorite = favoriteService.isFavoriteMovie(appUser, movie);
            request.setAttribute("isFavorite", isFavorite);
            
            String userRole = "USER";
            if (userDetails != null) {
                userRole = userDetails.getRoleDisplayName();
            }
            request.setAttribute("userRole", userRole);
            
			return "movie-details";
		} catch (Exception e) {
			e.printStackTrace();
	        return "redirect:/home/getAllMovies";
		}
    }
	
	
	@PostMapping("updateAccount")
	public String updateUserByID(HttpServletRequest request, @RequestParam String firstName, @RequestParam String lastName, 
	                             @RequestParam String username, @RequestParam String password, 
	                             @RequestParam MultipartFile imagePath) {
	    UserDetailsImpl userDetails = (UserDetailsImpl) request.getSession().getAttribute("loggedUser");

	    if (userDetails == null) {
	        return "redirect:/auth/login";
	    }

	    int idUser = userDetails.getIdUser();
	    try {
	        AppUser updatedUser = userService.updateUserById(idUser, firstName, lastName, username, password, imagePath);

	        UserDetailsImpl updatedUserDetails = new UserDetailsImpl(updatedUser);

	        request.getSession().setAttribute("loggedUser", updatedUserDetails);

	        String message = "Profile updated!";
	        request.setAttribute("message", message);
	    } catch (RuntimeException e) {
	        e.printStackTrace();
	        String message = "Username is already taken. Please choose a different username.";
	        request.setAttribute("failMsg", message);
	    } catch (Exception e) {
	        e.printStackTrace();
	        String message = "Failed to update profile: " + e.getMessage();
	        request.setAttribute("failMsg", message);
	    }
	    return "edit-profile-user";
	}


	@PostMapping("saveComment/{idMovie}")
	public String saveComment(@PathVariable int idMovie, @RequestParam String text, HttpServletRequest request) {
		UserDetailsImpl userDetails = (UserDetailsImpl) request.getSession().getAttribute("loggedUser");
		int idUser = userDetails.getIdUser();
		userService.saveComment(idUser, idMovie, text);
		
		return "redirect:/user/getMovieDetails?idMovie=" + idMovie;
	}
	
	@PostMapping("deleteComment/{idMovie}")
	public String deleteComment(@PathVariable int idMovie, @RequestParam int idComment, Principal principal, HttpServletRequest request) {
		UserDetailsImpl userDetails = (UserDetailsImpl) request.getSession().getAttribute("loggedUser");
		int idUser = userDetails.getIdUser();
	    boolean isAdmin = ((AbstractAuthenticationToken) principal).getAuthorities().stream()
	                            .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
	    userService.deleteComment(idComment, idUser, isAdmin);
	    return "redirect:/user/getMovieDetails?idMovie=" + idMovie;
	}


}
