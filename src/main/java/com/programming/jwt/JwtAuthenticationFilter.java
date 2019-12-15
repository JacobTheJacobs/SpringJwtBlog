package com.programming.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//retirve the web token from the request
		String jwtFromRequest = getJwtFromRequest(request);
       //validation of token
		if(StringUtils.hasText(jwtFromRequest)&& jwtProvider.validateToken(jwtFromRequest)) {
			String username = jwtProvider.getUsernameFromJWT(jwtFromRequest);
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
			userDetails,null,userDetails.getAuthorities());
			
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					
					SecurityContextHolder.getContext().setAuthentication(authentication);		
		}
		//continue executing filters
		filterChain.doFilter(request, response);
		
	}

	//request as parameter
	private String getJwtFromRequest(HttpServletRequest request) {
		// get the request header
		String headerBearerToken = request.getHeader("Authorization");
		//if token has text 
		if(StringUtils.hasText(headerBearerToken)&&headerBearerToken.startsWith("Bearer ")) {
		//now we can be sure that is the bearer token
			return headerBearerToken.substring(7);
		}
		//return clean token
		return headerBearerToken;
		
	}
	

}
