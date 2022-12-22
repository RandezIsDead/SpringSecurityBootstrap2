package com.randez_trying.SpringSecurityBootstrap.controller;

import com.randez_trying.SpringSecurityBootstrap.model.Role;
import com.randez_trying.SpringSecurityBootstrap.model.User;
import com.randez_trying.SpringSecurityBootstrap.repository.RoleRepository;
import com.randez_trying.SpringSecurityBootstrap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService service;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;

    @Autowired
    public AdminController(UserService service, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.service = service;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    @GetMapping("/panel")
    public String adminPanel(Model model,@AuthenticationPrincipal User currentUser) {
        List<User> allUsers = service.getAllUsers();
        model.addAttribute("allUsers", allUsers);
        model.addAttribute("user", currentUser);
        model.addAttribute("newUser", new User());
        model.addAttribute("allRoles", roleRepository.findAll());
        return "admin";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        service.saveUser(user);
        return "redirect:/admin/panel";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") long id) {
        model.addAttribute("userEdit",service.getUserById(id));
        return "admin";
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public String update(@ModelAttribute("editUser") User user,
                         @PathVariable("id") long id) {
        List<Role> roleList = user.getRoles();
        Optional<Role> roleUser = roleRepository.findById(1L);
        Optional<Role> roleAdmin = roleRepository.findById(2L);
        if (roleList.get(0).getRole().equals("1")) {
            roleList.add(roleUser.get());
        } else if (roleList.get(0).getRole().equals("2")) {
            roleList.add(roleAdmin.get());
        }
        user.setRoles(roleList);
        user.setPassword(encoder.encode(user.getPassword()));
        service.updateUser(id, user);
        return "redirect:/admin/panel";
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public String delete(@PathVariable("id") long id) {
        service.deleteUser(id);
        return "redirect:/admin/panel";
    }
}
