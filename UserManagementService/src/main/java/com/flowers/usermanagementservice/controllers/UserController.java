package com.flowers.usermanagementservice.controllers;

import com.flowers.usermanagementservice.domain.User;
import com.flowers.usermanagementservice.domain.UserId;
import com.flowers.usermanagementservice.services.UserService;
import com.flowers.usermanagementservice.services.dto.LoginRequest;
import com.flowers.usermanagementservice.services.dto.LoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> list = userService.getUsers();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<User>> getUsersByType(@PathVariable String type) {
        List<User> list = userService.getUsersByType(type);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        if (user.getId() == null) {
            user.setId(new UserId(0));
        }
        boolean result = userService.createUser(user);
        if (result) {
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create user.");
    }

    @PutMapping
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        if (user.getId() == null) {
            return ResponseEntity.badRequest().body("User ID is required for update.");
        }
        boolean result = userService.updateUser(user);
        if (result) {
            return ResponseEntity.ok("User updated successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found or update failed.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        boolean result = userService.deleteUser(id);
        if (result) {
            return ResponseEntity.ok("User deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found or delete failed.");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
