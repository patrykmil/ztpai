package iu.iu.spring_app.users.controller;

import iu.iu.spring_app.errors.ResourceNotFoundException;
import iu.iu.spring_app.users.model.User;
import iu.iu.spring_app.users.service.AuthenticationService;
import iu.iu.spring_app.users.service.DeleteUserService;
import iu.iu.spring_app.users.service.GetUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/users")
@RestController
public class UserController {
    private final GetUserService getUserService;
    private final AuthenticationService authenticationService;
    private final DeleteUserService deleteUserService;

    public UserController(GetUserService getUserService, AuthenticationService authenticationService, DeleteUserService deleteUserService) {
        this.getUserService = getUserService;
        this.authenticationService = authenticationService;
        this.deleteUserService = deleteUserService;
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = getUserService.getAllUsers();
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No users found");
        }
        return ResponseEntity.ok(getUserService.getAllUsers());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        User user = getUserService.getUserById(id);
        if (user == null) {
            throw new ResourceNotFoundException("User " + id + " not found");
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/get/email")
    public ResponseEntity<User> getUserByEmail(@RequestBody Map<String, String> payload) {
        User user = getUserService.getUserByEmail(payload);
        if (user == null) {
            throw new ResourceNotFoundException("User " + payload.get("email") + " not found");
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/get/by/username")
    public ResponseEntity<User> getUserByUsername(@RequestBody Map<String, String> payload) {
        User user = getUserService.getUserByUsername(payload);
        if (user == null) {
            throw new ResourceNotFoundException("User " + payload.get("username") + " not found");
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> payload) {
        if (payload.get("email") == null || payload.get("password") == null || payload.get("username") == null) {
            throw new ResourceNotFoundException("User " + payload.get("email") + " not found");
        }
        return authenticationService.register(payload);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> payload) {
        if (payload.get("email") == null || payload.get("password") == null) {
            throw new ResourceNotFoundException("User " + payload.get("email") + " not found");
        }
        return authenticationService.login(payload);
    }

    @DeleteMapping("/delete/email")
    public ResponseEntity<?> deleteByEmail(@RequestBody Map<String, String> payload) {
        deleteUserService.deleteUserByEmail(payload);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/username")
    public ResponseEntity<?> deleteByUsername(@RequestBody Map<String, String> payload) {
        deleteUserService.deleteUserByUsername(payload);
        return ResponseEntity.ok().build();
    }
}