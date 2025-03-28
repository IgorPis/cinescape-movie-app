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
    
  //FINAL REGISTRATION
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
    
////////////
    
//NAVODNO OVO NECE BITI POTREBNO JER CE SE LOGOUT RADITI NA FRONTU GDE CEMO SAMO OBRISATI TOKEN OD USERA SA LOCAL STORAGE(STORAGE U BROWSERU)
//    @PostMapping("/logout")
//    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
//        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
//            String jwt = token.substring(7);
//            tokenBlacklistService.blacklistToken(jwt);
//            logger.info("JWT token blacklisted: {}", jwt);
//            return ResponseEntity.ok("Successfully logged out");
//        }
//        logger.warn("Invalid token for logout: {}", token);
//        return ResponseEntity.badRequest().body("Invalid token");
//    }
	
//	GENUINE CODER TUTORIAL
//	@PostMapping("register/user")
//	public ResponseEntity<String> createUser(@RequestBody RegisterDto registerDto) {
//		if(userRepo.existsByUsername(registerDto.getUsername())) {
//			return new ResponseEntity<String>("Username is taken", HttpStatus.BAD_REQUEST);
//		}
//		AppUser user = new AppUser();
//		user.setUsername(registerDto.getUsername());
//		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
//		Role role = roleRepo.findById(registerDto.getIdRole()).get();
//		user.setRole(role);
//		userRepo.save(user);
//		return new ResponseEntity<>("User registered success", HttpStatus.OK);
//	}
	
//	@PostMapping("login/user")
//	public String authenticateAndGetToken(@RequestBody LoginDto loginDto) {
//		Authentication authentication = authenticationManager.authenticate(
//				new UsernamePasswordAuthenticationToken(
//				loginDto.getUsername(), loginDto.getPassword()));
//		if(authentication.isAuthenticated()) {
//			return jwtService.generateToken(customUserDetailsService.loadUserByUsername(loginDto.getUsername()));
//		} else {
//			throw new UsernameNotFoundException("Invalid credentials.");
//		}
//	}
	
//	CODE ENDS
	
	
	

	
//    @PostMapping("login1")
//	public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {
//		Authentication authentication = authenticationManager.authenticate(
//				new UsernamePasswordAuthenticationToken(
//				loginDto.getUsername(), 
//				loginDto.getPassword()));
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//		String token = jwtService.generateToken(customUserDetailsService.loadUserByUsername(loginDto.getUsername()));
//		return new ResponseEntity<>(new AuthResponseDto(token), HttpStatus.OK);
//		
//	}
	
//	@PostMapping("register")
//	public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
//		if(userRepo.existsByUsername(registerDto.getUsername())) {
//			return new ResponseEntity<String>("Username is taken", HttpStatus.BAD_REQUEST);
//		}
//		
//		AppUser user = new AppUser();
//		user.setUsername(registerDto.getUsername());
//		user.setPassword(passwordEncoder.encode((registerDto.getPassword())));
//		
//		Role role = roleRepo.findById(registerDto.getIdRole()).get();
//		user.setRole(role);
//		userRepo.save(user);
//		
//		return new ResponseEntity<>("User registered success", HttpStatus.OK);
//		
//	}
	
}
