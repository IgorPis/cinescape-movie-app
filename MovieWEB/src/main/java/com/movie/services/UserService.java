package com.movie.services;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.movie.repositories.CommentRepository;
import com.movie.repositories.MovieRepository;
import com.movie.repositories.UserRepository;

import model.AppUser;
import model.Comment;
import model.Movie;

@Service
public class UserService {
	
	@Value("${user.upload-dir}")
	private String userUploadDir;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private CommentRepository commentRepo;

	@Autowired
	private MovieRepository movieRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional(readOnly = true)
	public List<AppUser> getAllUsers() {
		return userRepo.findAll();
	}

	@Transactional(readOnly = true)
	public AppUser getUserById(int idUser) {
		return userRepo.findById(idUser).orElseThrow(() -> new RuntimeException("User byID not found"));
	}
	
	@Transactional
	public AppUser updateUserById(int idUser, String firstName, String lastName, String username, String password, MultipartFile imagePath) throws IOException {
	    AppUser user = userRepo.findById(idUser).orElseThrow(() -> new RuntimeException("User not found"));

	    Optional<AppUser> existingUser = userRepo.findByUsername(username);
	    if (existingUser.isPresent() && existingUser.get().getIdUser() != idUser) {
	        throw new RuntimeException("Username already exists. Please choose a different username.");
	    }

	    String picturePath;
	    if (imagePath != null && !imagePath.isEmpty()) {
	        String fileName = imagePath.getOriginalFilename();
	        File directory = new File(userUploadDir);
	        if (!directory.exists()) {
	            directory.mkdirs();
	        }
	        File serverFile = new File(directory.getAbsolutePath() + File.separator + fileName);
	        imagePath.transferTo(serverFile);
	        picturePath = "/CineScape/imgUsers/" + fileName;
	    } else {
	        picturePath = user.getImagePath() != null ? user.getImagePath() : "/CineScape/imgUsers/default.jpg";
	    }

	    user.setFirstName(firstName);
	    user.setLastName(lastName);
	    user.setUsername(username);
	    user.setPassword(passwordEncoder.encode(password));
	    user.setImagePath(picturePath);
	    userRepo.save(user);

	    return user;
	}


	@Transactional
	public String deleteUserById(int idUser) {
		AppUser user = userRepo.findById(idUser).orElseThrow(() -> new RuntimeException("User delete not found"));
		userRepo.delete(user);
		return "Deleted user with the ID: " + idUser;
	}

	@Transactional
	public String saveComment(int idUser, int idMovie, String text) {
		AppUser user = userRepo.findById(idUser).orElseThrow(() -> new RuntimeException("User not found"));
		Movie movie = movieRepo.findById(idMovie).orElseThrow(() -> new RuntimeException("Movie not found"));

		Comment comment = new Comment();
		comment.setAppUser(user);
		comment.setMovie(movie);
		comment.setText(text);
		comment.setTimestamp(convertToDate(LocalDateTime.now()));
		commentRepo.save(comment);

		return "Comment saved";
	}

	private Date convertToDate(LocalDateTime dateTime) {
		return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
	}
	
	@Transactional
	public String deleteComment(int idComment, int idUser, boolean isAdmin) {
	    Comment comment = commentRepo.findById(idComment)
	            .orElseThrow(() -> new RuntimeException("Comment not found"));

	    if (isAdmin || comment.getAppUser().getIdUser() == idUser) {
	        commentRepo.delete(comment);
	        return "Comment deleted successfully";
	    } else {
	        throw new RuntimeException("You do not have permission to delete this comment");
	    }
	}

	public List<Comment> getCommentsOfMovie(Movie movie) {
		return commentRepo.findByMovie(movie);
	} 

}