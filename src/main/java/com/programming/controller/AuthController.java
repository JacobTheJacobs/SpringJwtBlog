package com.programming.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import com.programming.dto.LoginRequest;
import com.programming.dto.RegisterRequest;
import com.programming.service.AuthService;

@RestController
@RequestMapping(value="/api/auth")
public class AuthController {
	
	   @Autowired
	    private AuthService authService;
	 
	    @PostMapping("/signup")
	    public ResponseEntity signup(@RequestBody RegisterRequest registerRequest) {
	        authService.signup(registerRequest);
	        return new ResponseEntity(HttpStatus.OK);
	    }
	    
	    @PostMapping("/login")
	    public String login(@RequestBody LoginRequest loginRequest) {
	    	String login = authService.login(loginRequest);
			return login;
	    	
	    }
	}
