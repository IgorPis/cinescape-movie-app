
package com.movie.services;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.movie.repositories.RoleRepository;
import com.movie.repositories.UserRepository;

import model.AppUser;
import model.Role;

@Service
public class AuthService {
	
	@Value("${user.upload-dir}")
	private String userUploadDir;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<Role> getAllRoles() {
		return roleRepo.findAll();
	}

    @Transactional
    public String registerUserWithImage(String firstName, String lastName, String username, 
    		String password, MultipartFile imagePath, int idRole) throws IOException, RuntimeException {
        if (username == null || username.isEmpty()) {
            return "Username field cannot be empty";
        }
        if (userRepo.existsByUsername(username)) {
            return "Username is taken";
        }
        String fileName = imagePath.getOriginalFilename();
        File directory = new File(userUploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String picturePath;
        if (fileName != null && !fileName.isEmpty()) {
            File serverFile = new File(directory.getAbsolutePath() + File.separator + fileName);
            imagePath.transferTo(serverFile);
            picturePath = "/CineScape/imgUsers/" + fileName;
        } else {
            picturePath = "/CineScape/imgUsers/default.jpg";
        }
        Role role = roleRepo.findById(idRole).orElseThrow(() -> new RuntimeException("Role not found"));
        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setImagePath(picturePath);
        user.setRole(role);
        userRepo.save(user);
        return "Registered successfully!";
    }
}
