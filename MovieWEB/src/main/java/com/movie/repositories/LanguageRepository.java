package com.movie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Language;

public interface LanguageRepository extends JpaRepository<Language, Integer> {

}
