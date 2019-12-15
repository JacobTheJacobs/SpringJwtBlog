package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.programming.controller")
@ComponentScan(basePackages = "com.programming.config")
@ComponentScan(basePackages = "com.programming.dto")
@ComponentScan(basePackages = "com.programming.service")
@ComponentScan(basePackages = "com.programming.jwt")
@EntityScan({"com.programming.model"})
@EnableJpaRepositories(basePackages = {"com.programming.repository"})
public class BlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

}
