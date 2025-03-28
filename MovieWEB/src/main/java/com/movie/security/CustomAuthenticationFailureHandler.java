package com.movie.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Autowired
	private CustomUserDetailsService userService;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		String errorMessage = "Invalid credentials.";

		if (exception instanceof BadCredentialsException) {
			String username = request.getParameter("username");
			try {
				if (username != null && userService.loadUserByUsername(username) != null) {
					errorMessage = "Incorrect password.";
				} else {
					errorMessage = "Incorrect username.";
				}
			} catch (UsernameNotFoundException e) {
				errorMessage = "Incorrect username.";
			}
		} else if (exception instanceof UsernameNotFoundException) {
			errorMessage = "Incorrect username.";
		}

		request.getSession().setAttribute("errorMessage", errorMessage);
		response.sendRedirect(request.getContextPath() + "/auth/login?error=true");
	}

}
