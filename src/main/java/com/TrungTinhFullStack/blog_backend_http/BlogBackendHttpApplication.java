package com.TrungTinhFullStack.blog_backend_http;

import com.TrungTinhFullStack.blog_backend_http.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BlogBackendHttpApplication {

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(BlogBackendHttpApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			userService.createInitialAdmin();
		};
	}
}
