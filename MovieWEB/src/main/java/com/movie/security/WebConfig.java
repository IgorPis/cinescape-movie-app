package com.movie.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {


	@Value("${file.upload-dir}")
	private String movieUploadDir;

	@Value("${user.upload-dir}")
	private String userUploadDir;
	
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/imgMovies/**")
                .addResourceLocations("file:" + movieUploadDir + "/");

        registry.addResourceHandler("/imgUsers/**")
                .addResourceLocations("file:" + userUploadDir + "/");
    }

}