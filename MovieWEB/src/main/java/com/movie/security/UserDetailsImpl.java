package com.movie.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import model.AppUser;

public class UserDetailsImpl implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	private AppUser appUser;
	
    public UserDetailsImpl(AppUser appUser) {
        this.appUser = appUser;
    }
	
	@Override
	public Collection<GrantedAuthority> getAuthorities() {
	    Collection<GrantedAuthority> authorities = new ArrayList<>();
	    authorities.add(new SimpleGrantedAuthority("ROLE_"+appUser.getRole().getRole()));
	    return authorities;
	}
	
    public String getRoleDisplayName() {
        return appUser.getRole().getRole();
    }
    
    public AppUser getAppUser() {
    	return appUser;
    }
	
	public int getIdUser() {
		return appUser.getIdUser();
	}
	
	public String getFirstName() {
		return appUser.getFirstName();
	}
	
	public String getLastName() {
		return appUser.getLastName();
	}
	
	public String getImagePath() {
		return appUser.getImagePath();
	}
	
	@Override
	public String getUsername() {
		return appUser.getUsername();
	}
	@Override
	public String getPassword() {
		return appUser.getPassword();
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}



}
