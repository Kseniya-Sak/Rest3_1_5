package ru.kata.spring.boot_security.demo.util;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.annotation.PostConstruct;
import java.util.Set;

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
        Role role1 = new Role(1l,"ADMIN");
        Role role2 = new Role( 2l,"USER");
        roleService.save(role1);
        roleService.save(role2);

        User user1 = new User("Admin", "Sims", 25, "admin","admin@mail.ru", Set.of(role1));
        userService.save(user1);

        User user2 = new User("User", "Dark", 20, "user", "user@mail.ru", Set.of(role2));
        userService.save(user2);

        User user3 = new User("Ira", "Makarova", 32, "100", "ira@gmail.com", Set.of(role1, role2));
        userService.save(user3);

        User user4 = new User("Igor", "Matusevich", 28, "100","igor@gmail.com",  Set.of(role1));
        userService.save(user4);

    }

}
