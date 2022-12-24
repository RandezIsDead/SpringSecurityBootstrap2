package com.randez_trying.SpringSecurityBootstrap.controller;

import com.randez_trying.SpringSecurityBootstrap.model.Role;
import com.randez_trying.SpringSecurityBootstrap.model.User;
import com.randez_trying.SpringSecurityBootstrap.service.RoleServiceImpl;
import com.randez_trying.SpringSecurityBootstrap.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;

    @Autowired
    public AdminController(UserServiceImpl userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/panel")
    public String adminPanel(Model model, Principal principal) {
        User user = userService.findUserByUsername(principal.getName());
        model.addAttribute("allUsers", userService.getAllUsers());
        model.addAttribute("user", userService.getUser(user.getId()));
        model.addAttribute("newUser", new User());
        return "admin";
    }

    @GetMapping("/new")
    public String newUserForm(Model model, @ModelAttribute("user") User user) {
        List<Role> roles = roleService.getUniqAllRoles();
        model.addAttribute("rolesAdd", roles);
        return "admin";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") User user) {
        userService.createNewUser(user);
        return "redirect:/admin/panel";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("userEdit", userService.getUser(id));
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("rolesAdd", roles);
        return "admin";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("editUser") User user) {
        userService.updateUser(user);
        return "redirect:/admin/panel";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/panel";
    }
}
