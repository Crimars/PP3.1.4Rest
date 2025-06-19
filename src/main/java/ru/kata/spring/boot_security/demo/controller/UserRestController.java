package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;


@RestController
@RequestMapping("/api/admin")
public class UserRestController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Получить пользователя по ID
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id).orElse(null);
        return (user != null) ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    // Добавить нового пользователя
    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        if (userService.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.badRequest().body("User with that email already exists");
        }
        boolean success = userService.createUser(user);
        return success ? ResponseEntity.ok("User created") : ResponseEntity.badRequest().build();
    }

    // Обновить пользователя
    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User existingUser = userService.getUserById(id).orElse(null);
        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }
        updatedUser.setId(id);
        boolean success = userService.updateUser(updatedUser);
        return success ? ResponseEntity.ok("User updated") : ResponseEntity.badRequest().build();
    }

    // Удалить пользователя
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean success = userService.deleteUser(id);
        return success ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Получить все роли
    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());

    }
}