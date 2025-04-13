package iu.iu.spring_app.api.users.controller;

import iu.iu.spring_app.api.errors.ResourceNotFoundException;
import iu.iu.spring_app.api.users.model.User;
import iu.iu.spring_app.api.users.service.DeleteUserService;
import iu.iu.spring_app.api.users.service.GetUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/api/users")
@RestController
public class UserController {
    private final GetUserService getUserService;
    private final DeleteUserService deleteUserService;

    public UserController(GetUserService getUserService, DeleteUserService deleteUserService) {
        this.getUserService = getUserService;
        this.deleteUserService = deleteUserService;
    }

    @GetMapping("/get/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = getUserService.getAllUsers();
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No users found");
        }
        return ResponseEntity.ok(getUserService.getAllUsers());
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        User user = getUserService.getUserById(id);
        if (user == null) {
            throw new ResourceNotFoundException("User " + id + " not found");
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/get/email")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserByEmail(@RequestBody Map<String, String> payload) {
        User user = getUserService.getUserByEmail(payload);
        if (user == null) {
            throw new ResourceNotFoundException("User " + payload.get("email") + " not found");
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/get/by/username")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserByUsername(@RequestBody Map<String, String> payload) {
        User user = getUserService.getUserByUsername(payload);
        if (user == null) {
            throw new ResourceNotFoundException("User " + payload.get("username") + " not found");
        }
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/delete/email")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteByEmail(@RequestBody Map<String, String> payload) {
        deleteUserService.deleteUserByEmail(payload);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/username")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteByUsername(@RequestBody Map<String, String> payload) {
        deleteUserService.deleteUserByUsername(payload);
        return ResponseEntity.ok().build();
    }
}