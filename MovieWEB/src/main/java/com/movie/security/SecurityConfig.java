package com.movie.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
            	.requestMatchers("/styles/**", "/js/**", "/images/**").permitAll()
            	.requestMatchers("/auth/**", "/home/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/fav/**").hasAnyRole("USER", "ADMIN")
//                .anyRequest().authenticated()
                .anyRequest().permitAll()
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/auth/login")
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .failureHandler(customAuthenticationFailureHandler())
                .defaultSuccessUrl("/home/getAllMovies", true)
            )
            .logout(logout -> logout
                    .logoutUrl("logout") // URL to trigger logout
                    .logoutSuccessUrl("/auth/login?logout=success") // Redirect URL after logout
                    .invalidateHttpSession(true) // Invalidate the session
                    .deleteCookies("JSESSIONID") // Delete session cookies
                )
            .exceptionHandling(exceptionHandling -> exceptionHandling
                    .authenticationEntryPoint((request, response, authException) -> {
                        response.sendRedirect(request.getContextPath() + "/auth/login?message=loginFirst");
                    })
                );
        return http.build();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }
	
	
//  NE TREBAJU MI JER IH SPRING AUTOMATSKI DETEKTUJE BEZ DA IH EXPLICITNO NAVODIM
//	@Autowired
//	private CustomUserDetailsService userDetailsService;
//  
//	@Bean
//	public UserDetailsService userDetailsService() {
//		return userDetailsService;
//	}
//	NE TREBAJU MI JER IH SPRING AUTOMATSKI DETEKTUJE BEZ DA IH EXPLICITNO NAVODIM
//	@Bean
//	public AuthenticationProvider authenticationProvider() {
//		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//		provider.setUserDetailsService(userDetailsService);
//		provider.setPasswordEncoder(passwordEncoder());
//		return provider;
//	}
	
}