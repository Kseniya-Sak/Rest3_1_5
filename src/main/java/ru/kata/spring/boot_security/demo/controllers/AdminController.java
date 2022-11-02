package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

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
        System.out.println("this id " + id);
        List<Role> listRoles = roleService.getAllRoles();
        model.addAttribute("user", userService.findOne(id));
        model.addAttribute("listRoles", listRoles);

        return "admin/edit_form";
    }

    @GetMapping("/new")
    public String newPerson(Model model) {
        List<Role> listRoles = roleService.getAllRoles();
        model.addAttribute("user", new User());
        model.addAttribute("listRoles", listRoles);
        return "admin/new";
    }

    @PostMapping
    public String create(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "admin/new";

        userService.save(user);
        return "redirect:/admin";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                         @PathVariable("id") long id) {
        if (bindingResult.hasErrors())
            return "edit_form";
        userService.update(id, user);
        return "redirect:/admin";
    }


    //
//    roles = roles.replace("[", "").replace("]", "");
//    String[] roleNames = roles.split(",");
//
//		for (String aRoleName : roleNames) {
//        userDetails.addRole(new Role(aRoleName));
//    }
//    //




    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
