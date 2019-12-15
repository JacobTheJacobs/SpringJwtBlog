package com.programming.jwt;

import java.security.Key;

import javax.annotation.PostConstruct;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtProvider {
	
	private Key key;
	
	//creating key once for resigning JWT every time
	@PostConstruct
	public void init() {
		key=Keys.secretKeyFor(SignatureAlgorithm.HS512);
	}

	public String generateToken(Authentication authentication) {
		//Retrieving the principal from authentication object and casting it to user.core
		User principal =(User) authentication.getPrincipal();
		// signing the jwt 
		return Jwts.builder()
				.setSubject(principal.getUsername())//providing the username
				.signWith(key)//key algorithm
				.compact();
		
	}
	
	//validating and returning true
	public boolean validateToken(String jwt){
		Jwts.parser().setSigningKey(key).parseClaimsJws(jwt);
		return true;
		
	}

	
	//pass the token and retrieve the body of the claims we have added
	public String getUsernameFromJWT(String token) {
		// from the claims we will return the subject
		Claims claims = Jwts.parser()
		          .setSigningKey(key)
		          .parseClaimsJws(token)
		          .getBody();
		
		return claims.getSubject();
		
	}
}
