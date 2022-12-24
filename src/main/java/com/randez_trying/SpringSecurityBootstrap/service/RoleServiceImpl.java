package com.randez_trying.SpringSecurityBootstrap.service;

import com.randez_trying.SpringSecurityBootstrap.model.Role;
import com.randez_trying.SpringSecurityBootstrap.repositories.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;


    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findRoleOfId(Long id) {
        return roleRepository.findById(id).get();
    }


    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }



    public List<Role> getUniqAllRoles() {
        return roleRepository.findAll();
    }

    @Transactional
    public void addRole(Role role) {
        roleRepository.save(role);
    }



}
