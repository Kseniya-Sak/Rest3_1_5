package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    private static final List<Role> listOfRoles;

    static {
        listOfRoles = List.of(new Role(1l, "ROLE_ADMIN"), new Role(2l, "ROLE_USER"));
    }

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());

        return "admin/users";
    }

    @GetMapping("/{id}/edit")
    public String show(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.findOne(id));
        model.addAttribute("listRoles", roleService.getAllRoles());

        return "admin/edit_form";
    }

    @GetMapping("/new")
    public String newPerson(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("listRoles", roleService.getAllRoles());

        return "admin/new";
    }

    @PostMapping
    public String create(@ModelAttribute("user") User user, BindingResult bindingResult) {
        userService.save(user);

        return "redirect:/admin";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("user") User user, BindingResult bindingResult,
                         @PathVariable("id") long id) {
        userService.update(id, user);

        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
