package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    private final UserValidator userValidator;

    public AdminController(UserService userService, RoleService roleService, UserValidator userValidator) {
        this.userService = userService;
        this.roleService = roleService;
        this.userValidator = userValidator;
    }

    @GetMapping
    @PreAuthorize("!hasRole('USER')")
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());

        return "admin/users";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("!hasRole('USER')")
    public String show(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.findOne(id));
        model.addAttribute("listRoles", roleService.getAllRoles());

        return "admin/edit_form";
    }

    @GetMapping("/new")
    @PreAuthorize("!hasRole('USER')")
    public String newPerson(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("listRoles", roleService.getAllRoles());

        return "admin/new";
    }

    @PostMapping
    @PreAuthorize("!hasRole('USER')")
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/new";
        }
        userService.save(user);

        return "redirect:/admin";
    }

    @PostMapping("/{id}")
    @PreAuthorize("!hasRole('USER')")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                         @PathVariable("id") long id) {
        if (bindingResult.hasErrors())
            return "admin/edit_form";
        userService.update(id, user);

        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("!hasRole('USER')")
    public String delete(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
