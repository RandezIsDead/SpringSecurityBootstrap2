package com.randez_trying.SpringSecurityBootstrap.service;

import com.randez_trying.SpringSecurityBootstrap.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    public List<User> getAllUsers();

    public User getUserById(long id);

    public void saveUser(User user);

    public void updateUser(long id, User updatedUser);

    public void deleteUser(long id);
}