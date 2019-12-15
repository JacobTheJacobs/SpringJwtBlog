package com.programming.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.programming.jwt.JwtAuthenticationFilter;
 

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	
	@Autowired
	//uses user detail service Implementation class
	private UserDetailsService userDetailsService;
	
	//uses user detail service to look for the users
	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception{
		return super.authenticationManagerBean();
	}
	
	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	   @Override
	    public void configure(HttpSecurity httpSecurity) throws Exception {
	        httpSecurity.csrf().disable()
	                .authorizeRequests()
	                .antMatchers("/api/auth/**")
	                .permitAll()
	                .antMatchers("/api/posts/all")
	                .permitAll()
	                .anyRequest()
	                .authenticated();
	        
	        httpSecurity.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	    }
	 
	    @Bean
	    PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

	    //read user from data base
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//decoding the password
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		}
	    
	    
	
	
}
