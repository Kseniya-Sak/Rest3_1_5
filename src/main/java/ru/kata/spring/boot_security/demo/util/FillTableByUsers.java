package ru.kata.spring.boot_security.demo.util;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class FillTableByUsers {
    private final UserService userService;
    private final RoleService roleService;

    public FillTableByUsers(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void fillTable() {
        roleService.save(new Role("ROLE_ADMIN"));
        roleService.save(new Role("ROLE_USER"));
        List<Role> roles = roleService.getAllRoles();

        User user1 = new User("Max", "Sims", 25, "100", List.of(roles.get(0)));
        userService.save(user1);

        User user2 = new User("Elena", "Dark", 20, "100",List.of(roles.get(0),roles.get(1)));
        userService.save(user2);

        User user3 = new User("Ira", "Makarova", 32, "100", List.of(roles.get(1)));
        userService.save(user3);

        User user4 = new User("Igor", "Matusevich", 28, "100", List.of(roles.get(0),roles.get(1)));
        userService.save(user4);
    }

}
