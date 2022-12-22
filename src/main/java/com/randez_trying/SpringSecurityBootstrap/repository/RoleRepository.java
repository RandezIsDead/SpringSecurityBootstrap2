package com.randez_trying.SpringSecurityBootstrap.repository;

import com.randez_trying.SpringSecurityBootstrap.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}