package com.randez_trying.SpringSecurityBootstrap;

import com.randez_trying.SpringSecurityBootstrap.model.Role;
import com.randez_trying.SpringSecurityBootstrap.model.User;
import com.randez_trying.SpringSecurityBootstrap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class SpringSecurityBootstrapApplication implements CommandLineRunner {

	private final UserService userService;

	@Autowired
	public SpringSecurityBootstrapApplication(UserService userService) {
		this.userService = userService;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityBootstrapApplication.class, args);
	}

	@Override
	public void run(String... args) {
		Role ar = new Role("ROLE_ADMIN");
		Role ur = new Role("ROLE_USER");

		User user = new User("user", "user", "user", "$2a$10$Yj78v4Qc/mf5cIC4yhB.iOf/js6dD0Mp2QLltcag36kR8PKanCI/S", List.of(ur));
		User admin = new User("admin", "admin", "admin", "$2y$10$08fime4hWZ5TMO.JkPEmXuIwyBchRDIbR/5QqtOnDtXE1s1LV52De", List.of(ar));

		userService.saveUser(admin);
		userService.saveUser(user);
	}
}
