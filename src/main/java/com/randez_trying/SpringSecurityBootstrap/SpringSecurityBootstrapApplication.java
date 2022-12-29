package com.randez_trying.SpringSecurityBootstrap;

import com.randez_trying.SpringSecurityBootstrap.model.Role;
import com.randez_trying.SpringSecurityBootstrap.model.User;
import com.randez_trying.SpringSecurityBootstrap.repositories.RoleRepository;
import com.randez_trying.SpringSecurityBootstrap.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SpringSecurityBootstrapApplication implements CommandLineRunner {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;

	@Autowired
	public SpringSecurityBootstrapApplication(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityBootstrapApplication.class, args);
	}

	@Override
	public void run(String... args) {
		Role roleAdmin = new Role("ROLE_ADMIN");
		Role roleUser = new Role("ROLE_USER");
		List<Role> adminRoles = new ArrayList<>();
		List<Role> userRoles = new ArrayList<>();
		roleRepository.save(roleAdmin);
		roleRepository.save(roleUser);
		adminRoles.add(roleAdmin);
		adminRoles.add(roleUser);
		userRoles.add(roleUser);

		User userAdmin = new User("admin", "admin", "admin", "$2y$10$08fime4hWZ5TMO.JkPEmXuIwyBchRDIbR/5QqtOnDtXE1s1LV52De", adminRoles);
		User userUser = new User("user", "user", "user", "$2y$10$GuP0CFLp71MpXFxHluKAy.t391.yfdkrTdSp6XRjvv2tnzGsTeH8O", userRoles);
		userRepository.save(userAdmin);
		userRepository.save(userUser);
	}
}
