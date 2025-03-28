package com.movie.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.movie.security.UserDetailsImpl;
import com.movie.services.MovieService;
import com.movie.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import model.AppUser;
import model.Genre;
import model.Language;
import model.Movie;

@Controller
@RequestMapping("/admin/")
public class AdminController {

	@Autowired
	private MovieService movieService;

	@Autowired
	private UserService userService;

//	FUNCTIONALITY FOR MOVIES

	@GetMapping("getGenreLang")
	public String getGenreLang(HttpServletRequest request) {
		List<Genre> genres = movieService.getGenres();
		request.getSession().setAttribute("genres", genres);
		List<Language> langs = movieService.getLang();
		request.getSession().setAttribute("langs", langs);
		return "add-movie";
	}

	@PostMapping("addMovie")
	public String addMovie(@RequestParam("posterPath") MultipartFile posterPath, @RequestParam String title,
			@RequestParam int duration, @RequestParam String description, @RequestParam int releaseYear,
			@RequestParam String trailerKey, @RequestParam int idGenre, @RequestParam int idLang,
			HttpServletRequest request) {
		try {
			String message = movieService.saveMovieWithImage(posterPath, title, duration, description, releaseYear,
					trailerKey, idGenre, idLang);
			request.setAttribute("message", message);
		} catch (Exception e) {
			e.printStackTrace();
			String message = "Failed to save movie: " + e.getMessage();
			request.setAttribute("message", message);
		}
		return "add-movie";
	}

	@GetMapping("editMoviePage/{idMovie}")
	public String editPage(@PathVariable int idMovie, HttpServletRequest request) {
		Movie selectedMovie = movieService.findMovieById(idMovie);
		request.setAttribute("movieById", selectedMovie);
		List<Genre> genres = movieService.getGenres();
		request.getSession().setAttribute("allgenres", genres);
		List<Language> langs = movieService.getLang();
		request.getSession().setAttribute("langs", langs);
		return "edit-movie";
	}

	@PostMapping("editMovie/{idMovie}")
	public String updateMovieById(@PathVariable int idMovie, @RequestParam String title, @RequestParam int duration,
			@RequestParam String description, @RequestParam int releaseYear, @RequestParam String trailerKey,
			@RequestParam("posterPath") MultipartFile posterPath, @RequestParam int idGenre, @RequestParam int idLang,
			HttpServletRequest request) {
		try {
			Movie updatedMovie = movieService.updateMovieById(idMovie, title, duration, description, releaseYear,
					trailerKey, posterPath, idGenre, idLang);

			// Set the updated movie object in the request
			request.setAttribute("movieById", updatedMovie);

			request.setAttribute("message", "Movie updated successfully!");
		} catch (Exception e) {
			e.printStackTrace();
			String message = "Failed to update movie: " + e.getMessage();
			request.setAttribute("message", message);
		}
		return "edit-movie";
	}

	@PostMapping("deleteMovie/{idMovie}")
	public String deleteMovieById(HttpServletRequest request, @PathVariable int idMovie) {
		try {
			movieService.deleteMovieById(idMovie);
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}
		return "redirect:/home/getAllMovies";
	}

//	END OF FUNCTIONALITY FOR MOVIES

//	FUNCTIONALITY FOR USERS

	@GetMapping("getAllUsers")
	public String allUsers(HttpServletRequest request) {
		List<AppUser> allUsers = userService.getAllUsers();
		request.setAttribute("allUsers", allUsers);
		return "all-users";
	}

	@GetMapping("getUserById")
	public String getUserId(@RequestParam int idUser, HttpServletRequest request) {
		try {
			AppUser user = userService.getUserById(idUser);
			request.setAttribute("userById", user);
			return "user-profile";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/admin/getAllUsers";
		}
	}

	@GetMapping("editUserPage/{idUser}")
	public String editUserPage(@PathVariable int idUser, HttpServletRequest request) {
		AppUser selectedUser = userService.getUserById(idUser);
		request.setAttribute("userById", selectedUser);
		return "edit-profile";
	}
	
	
	@PostMapping("updateUser/{idUser}")
	public String updateUserByID(@PathVariable int idUser, HttpServletRequest request,
	                             @RequestParam String firstName, @RequestParam String lastName,
	                             @RequestParam String username, @RequestParam String password,
	                             @RequestParam MultipartFile imagePath) {
	    AppUser targetUser = null; // Initialize the target user

	    try {
	        // Retrieve the authenticated user's details
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        Object principal = authentication.getPrincipal();

	        // Check if the principal is an instance of UserDetailsImpl
	        if (principal instanceof UserDetailsImpl) {
	            UserDetailsImpl userDetails = (UserDetailsImpl) principal;
	            AppUser authenticatedUser = userDetails.getAppUser();

	            // Retrieve the target user by ID
	            targetUser = userService.getUserById(idUser);

	            // Prevent editing of other admin profiles
	            if (targetUser.getRole().getRole().equals("ADMIN") && authenticatedUser.getIdUser() != idUser) {
	                String message = "Admins users cannot edit other admins profile.";
	                request.setAttribute("message", message);
	                request.setAttribute("userById", targetUser); // Ensure target user is still set
	                return "edit-profile"; // Or redirect to an appropriate error page
	            }

	            // Proceed with the update if not editing another admin
	            AppUser updatedUser = userService.updateUserById(idUser, firstName, lastName, username, password, imagePath);

	            // Set the updated user object in the request
	            request.setAttribute("userById", updatedUser);
	            String message = "Profile updated!";
	            request.setAttribute("message", message);
	        } else {
	            // If the principal is not authenticated properly, handle as unauthorized
	            String message = "Unauthorized action.";
	            request.setAttribute("message", message);
	            return "redirect:/auth/login"; // Redirect to login or handle as needed
	        }
	    } catch (RuntimeException e) {
	        // Handle custom exception when username already exists
	        e.printStackTrace();
	        String message = "Username is already taken. Please choose a different username.";
	        request.setAttribute("failMsg", message);
	        request.setAttribute("userById", targetUser); // Set targetUser in case of an exception
	    } catch (Exception e) {
	        e.printStackTrace();
	        String message = "Failed to update profile: " + e.getMessage();
	        request.setAttribute("failMsg", message);
	        request.setAttribute("userById", targetUser); // Set targetUser in case of an exception
	    }

	    // Ensure the user object is set in the request to avoid errors
	    if (targetUser != null) {
	        request.setAttribute("userById", targetUser);
	    }
	    return "edit-profile";
	}




	@PostMapping("deleteUser/{idUser}")
	public String deleteUser(HttpServletRequest request, @PathVariable int idUser) {
		// Get the authenticated user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();

		if (principal instanceof UserDetailsImpl) {
			UserDetailsImpl userDetails = (UserDetailsImpl) principal;
			AppUser authenticatedUser = userDetails.getAppUser();

			// Load the target user to be deleted
			AppUser targetUser = userService.getUserById(idUser); // Ensure this method retrieves the user by ID

			// Check for null to avoid null pointer exceptions
			if (authenticatedUser != null && targetUser != null) {
				String authenticatedUserRole = authenticatedUser.getRole().getRole(); // Should be "ADMIN" for admin
				String targetUserRole = targetUser.getRole().getRole(); // Should be "ADMIN" for admin

				// Debugging output
				System.out.println(
						"Authenticated User: " + authenticatedUser.getUsername() + " | Role: " + authenticatedUserRole);
				System.out.println("Target User: " + targetUser.getUsername() + " | Role: " + targetUserRole);

				// Check if the authenticated user is trying to delete their own account
				if (authenticatedUser.getIdUser() == idUser) {
					System.out.println("Deleting own account...");
					userService.deleteUserById(idUser);

					// Log the user out
					SecurityContextHolder.clearContext();
					request.getSession().invalidate();

					// Set the success message
					request.getSession().setAttribute("accountDeletedMessage", "Account deleted successfully!");

					return "redirect:/auth/login";
				}

				// Prevent admin from deleting another admin
				if ("ADMIN".equals(authenticatedUserRole) && "ADMIN".equals(targetUserRole)) {
					System.out.println("Admin cannot delete another admin.");
					request.getSession().setAttribute("deleteAdminMsg",
							"Admin users cannot delete other admin accounts!");
					return "redirect:/admin/getUserById?idUser=" + idUser;
				}

				// Admin deleting a non-admin user
				System.out.println("Admin deleting a non-admin user.");
				userService.deleteUserById(idUser);
				return "redirect:/admin/getAllUsers";
			}
		}
		
		return "redirect:/auth/login";
	}

//	END FUNCTIONALITY FOR USERS

}
