package ru.kata.spring.boot_security.demo.initialization;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;

@Component
public class InitDB {

    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public InitDB(RoleService roleService, UserService userService, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    private void fillDb() {

        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");

        User admin = new User("admin", "Альфия", "Дулькина", 20, "alfi@mail.ru", passwordEncoder.encode("root"));
        admin.addRole(roleService.add(roleAdmin));
        userService.add(admin);

        User user = new User("user", "Глафира", "Репкина", 25, "repka@mail.ru", passwordEncoder.encode("root"));
        user.addRole(roleService.add(roleUser));
        userService.add(user);

        User userAdmin = new User("moderator", "Петр", "Долгорукий", 30, "petro@mail.ru", passwordEncoder.encode("root"));
        userAdmin.addRole(roleService.add(roleUser));
        userAdmin.addRole(roleService.add(roleAdmin));
        userService.add(userAdmin);
    }
}
