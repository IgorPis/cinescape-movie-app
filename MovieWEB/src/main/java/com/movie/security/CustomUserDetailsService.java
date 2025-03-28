package com.movie.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.movie.repositories.UserRepository;

import model.AppUser;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser user = userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username + " not found."));
		return new UserDetailsImpl(user);
	}
	
//	1. MOZE DA RADI I BEZ USERDETAILSIMPL
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		AppUser user = userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username + " not found."));
//		
//		return new User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRole()));
//	}
//	1
//	private Collection<GrantedAuthority> mapRolesToAuthorities(Role role) {
//	    Collection<GrantedAuthority> authorities = new ArrayList<>();
//	    authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRole()));
//	    return authorities;
//	}

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		AppUser user = userRepo.findByUsername(username);
//		UserDetailsImpl userDetails = new UserDetailsImpl();
//		userDetails.setUsername(user.getUsername());
//		userDetails.setPassword(user.getPassword());
//		userDetails.setRoles(user.getRole());
//		return userDetails;
//	}

}
