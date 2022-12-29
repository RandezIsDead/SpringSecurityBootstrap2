package com.randez_trying.SpringSecurityBootstrap.controller;

import com.randez_trying.SpringSecurityBootstrap.model.User;
import com.randez_trying.SpringSecurityBootstrap.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RestSiteController {
    private final UserService userService;

    public RestSiteController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/current")
    public ResponseEntity<User> getUserPage(Principal principal) {
        return ResponseEntity.ok(userService.findUserByUsername(principal.getName()));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getOneUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PostMapping("/users")
    public ResponseEntity<?> createNewUser(@RequestBody User user) {
        userService.createNewUser(user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        User user = userService.getUser(id);
        userService.deleteUser(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/users")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return ResponseEntity.ok(user);
    }
}