package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    boolean createUser(User user);
    boolean deleteUser(Long userId);
    boolean updateUser(User user);
    User findByUsername(String username);
}