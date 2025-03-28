package com.movie.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.AppUser;
import model.Comment;
import model.Movie;

public interface CommentRepository extends JpaRepository<Comment, Integer>{
	
	List<Comment> findByMovie(Movie movie);
	
	List<Comment> findByAppUser(AppUser appUser);

}
