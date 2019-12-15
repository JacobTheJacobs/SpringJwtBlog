package com.programming.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.programming.dto.LoginRequest;
import com.programming.dto.RegisterRequest;
import com.programming.jwt.JwtProvider;
import com.programming.model.User;
import com.programming.repository.UserRepository;


@Service
public class AuthService {
	
	    @Autowired
	    private UserRepository userRepository;
	    @Autowired
	    private PasswordEncoder passwordEncoder;
	 
	    @Autowired
	    private AuthenticationManager authenticationManager;
	    
	    @Autowired
	    private JwtProvider jwtProvider;
	    
	    public void signup(RegisterRequest registerRequest) {
	        User user = new User();
	        user.setUserName(registerRequest.getUsername());
	        user.setEmail(registerRequest.getEmail());
	        user.setPassword(encodePassword(registerRequest.getPassword()));
	 
	        userRepository.save(user);
	    }
	 
	    private String encodePassword(String password) {
	        return passwordEncoder.encode(password);
	    }

		public String login(LoginRequest loginRequest) {
			//once the request will go throuth the user will be authenticated
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							loginRequest.getUsername(), loginRequest.getPassword()));
		//store written type of this authenticate method	
			SecurityContextHolder.getContext().setAuthentication(authentication);
			//creating web tokens after success
			return jwtProvider.generateToken(authentication);
			//will send it back to client
		
		}

		//current logged in user
		public Optional<org.springframework.security.core.userdetails.User> getCurrentUser() {
			//Retrieve user from the security context
			org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return Optional.of(principal);
		}

}
