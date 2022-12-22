package com.randez_trying.SpringSecurityBootstrap.service;

import com.randez_trying.SpringSecurityBootstrap.model.User;
import com.randez_trying.SpringSecurityBootstrap.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(long id) {
        Optional<User> user = repository.findById(id);
        ArrayList<User> list = new ArrayList<>();
        user.ifPresent(list::add);
        return list.get(0);
    }

    @Override
    public void saveUser(User user) {
        repository.save(user);
    }

    @Override
    public void updateUser(long id, User updatedUser) {
        updatedUser.setId(id);
        repository.save(updatedUser);
    }

    @Override
    public void deleteUser(long id) {
        repository.deleteById(id);
    }
}