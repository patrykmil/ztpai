package iu.iu.spring_app.users.controller;

import iu.iu.spring_app.users.model.User;
import iu.iu.spring_app.users.service.AddUserService;
import iu.iu.spring_app.users.service.GetUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    private final GetUserService getUserService;
    private final AddUserService addUserService;

    public UserController(GetUserService getUserService, AddUserService addUserService) {
        this.getUserService = getUserService;
        this.addUserService = addUserService;
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

    @PostMapping("/users/add")
    public ResponseEntity<?> addUser(@RequestBody Map<String, String> payload) {
        return addUserService.addUser(payload);
    }
}