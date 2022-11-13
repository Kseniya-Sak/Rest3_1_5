package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class AdminRestController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/user")
    public ResponseEntity<User> getAuthenticationAdmin(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(userService.findById(user.getId()), HttpStatus.OK);

    }

    @GetMapping("/admin")
    public ResponseEntity<List<UserDTO>> getListUsers() {
        List<UserDTO> usersDTOList = new ArrayList<>(userService.findAll()
                .stream()
                .map(a -> new UserDTO(a))
                .collect(Collectors.toList()));
        return usersDTOList.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(usersDTOList, HttpStatus.OK);
    }


    @GetMapping("/admin/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable long id) {
        User user = userService.findById(id);
        UserDTO userDTO = new UserDTO(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);

    }


    @PostMapping("/admin")
    public ResponseEntity<UserDTO> newUser(@RequestBody UserDTO user) {
        User userSave = userService.save(new User(user));
        UserDTO userDTO = new UserDTO(userSave);
        final URI location = ServletUriComponentsBuilder
                .fromCurrentServletMapping()
                .path("api/admin/{id}")
                .build()
                .expand(userDTO.getId()).toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);
        return new ResponseEntity<>(userDTO, headers, HttpStatus.CREATED);
    }


    @PutMapping("/admin")
    public ResponseEntity<UserDTO> editUser(@RequestBody UserDTO user) {
        User userEdit = userService.edit(new User(user));
        UserDTO userDTO = new UserDTO(userEdit);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }


    @DeleteMapping("/admin/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        User user = userService.findById(id);
        UserDTO userDTO = new UserDTO(user);

        if (userDTO == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Юзер с id " + id + " не найден");
        }
        userService.delete(new User(userDTO));
        return new ResponseEntity<>("Юзер  был удален id =  " + id, HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<Set<Role>> getAllRoles() {
        return new ResponseEntity<>(roleService.findAll(), HttpStatus.OK);
    }
}
