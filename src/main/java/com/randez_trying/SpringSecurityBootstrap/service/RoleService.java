package com.randez_trying.SpringSecurityBootstrap.service;


import com.randez_trying.SpringSecurityBootstrap.model.Role;

import java.util.List;

public interface RoleService {
    Role findRoleOfId(Long id);
    List<Role> getAllRoles();
    List<Role> getUniqAllRoles();
    void addRole(Role role);
}
