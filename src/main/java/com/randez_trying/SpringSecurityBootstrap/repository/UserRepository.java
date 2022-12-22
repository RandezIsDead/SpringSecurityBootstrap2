package com.randez_trying.SpringSecurityBootstrap.repository;


import com.randez_trying.SpringSecurityBootstrap.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}