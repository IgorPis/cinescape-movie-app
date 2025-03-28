package com.movie.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import model.AppUser;

public interface UserRepository extends JpaRepository<AppUser, Integer>{
	
	Optional<AppUser> findByUsername(String username);
	Boolean existsByUsername(String username);

}
