package com.movie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("model")
public class MovieWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieWebApplication.class, args);
	}

}
