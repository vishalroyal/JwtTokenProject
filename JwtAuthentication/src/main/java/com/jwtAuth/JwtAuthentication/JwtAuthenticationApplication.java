package com.jwtAuth.JwtAuthentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@ComponentScan(basePackages = "com.jwtAuth.JwtAuthentication.Security")
@ComponentScan(basePackages = "com.jwtAuth.JwtAuthentication.Config")

//@ComponentScan(basePackages = "com.jwtAuth.JwtAuthentication.Config")
public class JwtAuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtAuthenticationApplication.class, args);
	}

}
