package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.of(userService.getUserById(id));
    }

    @PostMapping("/users")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        boolean success = userService.createUser(user);
        if (success) {
            return ResponseEntity.ok("User created successfully");
        } else {
            return ResponseEntity.badRequest().body("Username already exists");
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        boolean success = userService.updateUser(user);
        if (success) {
            return ResponseEntity.ok("User updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean success = userService.deleteUser(id);
        return success ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}