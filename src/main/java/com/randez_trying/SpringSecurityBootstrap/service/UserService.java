package com.randez_trying.SpringSecurityBootstrap.service;


import com.randez_trying.SpringSecurityBootstrap.model.User;

import java.util.List;

public interface UserService {

    public List<User> getAllUsers();
    public void createNewUser(User user);
    public User getUser(Long id);
    public void updateUser(User user);
    public void deleteUser(Long id);
    public User findUserByUsername(String username);

}
