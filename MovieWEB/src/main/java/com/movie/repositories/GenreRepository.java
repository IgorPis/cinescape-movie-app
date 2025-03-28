package com.movie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Integer>{

}
