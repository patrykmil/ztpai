package iu.iu.spring_app.users.controller;

import iu.iu.spring_app.users.model.User;
import iu.iu.spring_app.users.service.AuthenticationService;
import iu.iu.spring_app.users.service.GetUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    private final GetUserService getUserService;
    private final AuthenticationService authenticationService;

    public UserController(GetUserService getUserService, AuthenticationService authenticationService) {
        this.getUserService = getUserService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/users/get/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(getUserService.getAllUsers());
    }

    @GetMapping("/users/get/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        User user = getUserService.getUserById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PostMapping("/users/get/email")
    public ResponseEntity<User> getUserByEmail(@RequestBody Map<String, String> payload) {
        User user = getUserService.getUserByEmail(payload);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PostMapping("/users/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> payload) {
        return authenticationService.register(payload);
    }

    @PostMapping("/users/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> payload) {
        return authenticationService.login(payload);
    }
}