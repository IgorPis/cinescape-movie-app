package com.movie.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.movie.services.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import model.Role;

@Controller
@RequestMapping("/auth/")
public class AuthController {
	
	@Autowired
	private AuthService authService;
    
    @GetMapping("getAllRoles")
    public String getAllRoles(HttpServletRequest request) {
    	List<Role> allRoles = authService.getAllRoles();
    	request.getSession().setAttribute("allRoles", allRoles);
    	return "sign-up";
    }
    
    @GetMapping("login")
    public String login() {
        return "login";
    }
    
   @GetMapping("custom-logout")
   public String logout(HttpServletRequest request) {
       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       if (auth != null) {
           SecurityContextHolder.getContext().setAuthentication(null);
       }
       request.getSession().invalidate();

       return "redirect:/auth/login?logout=success";
   }
    
    @PostMapping("register")
    public String registerUser(HttpServletRequest request, @RequestParam String firstName, @RequestParam String lastName, 
    		@RequestParam String username, @RequestParam String password, 
    		@RequestParam MultipartFile imagePath, @RequestParam int idRole) {
        try {
            String result = authService.registerUserWithImage(firstName, lastName, username, password, imagePath, idRole);
            if (result.equals("Registered successfully!")) {
            	request.setAttribute("resultMsg", result);
            } else if (result.equals("Username is taken") || result.equals("Username field cannot be empty")) {
            	request.setAttribute("failMsg", result);
            } else {
            	request.setAttribute("failMsg", "Failed to register");
            }
        } catch (IOException e) {
    		e.printStackTrace();
			String message = "Failed to upload image " + e.getMessage();
			request.setAttribute("failMsg", message);
        }
        return "sign-up";
    }
    
}
