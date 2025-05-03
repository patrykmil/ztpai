package iu.iu.spring_app.api.users.controller;

import iu.iu.spring_app.api.errors.ResourceNotFoundException;
import iu.iu.spring_app.api.users.model.User;
import iu.iu.spring_app.api.users.service.DeleteUserService;
import iu.iu.spring_app.api.users.service.GetUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@io.swagger.v3.oas.annotations.tags.Tag(name = "Users", description = "APIs for managing users (Admin only)")
public class UserController implements UserControllerInterface {
    private final GetUserService getUserService;
    private final DeleteUserService deleteUserService;

    public UserController(GetUserService getUserService, DeleteUserService deleteUserService) {
        this.getUserService = getUserService;
        this.deleteUserService = deleteUserService;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = getUserService.getAllUsers();
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No users found");
        }
        return ResponseEntity.ok(users);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserById(Integer id) {
        User user = getUserService.getUserById(id);
        if (user == null) {
            throw new ResourceNotFoundException("User " + id + " not found");
        }
        return ResponseEntity.ok(user);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserByEmail(Map<String, String> payload) {
        User user = getUserService.getUserByEmail(payload);
        if (user == null) {
            throw new ResourceNotFoundException("User " + payload.get("email") + " not found");
        }
        return ResponseEntity.ok(user);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserByUsername(Map<String, String> payload) {
        User user = getUserService.getUserByUsername(payload);
        if (user == null) {
            throw new ResourceNotFoundException("User " + payload.get("username") + " not found");
        }
        return ResponseEntity.ok(user);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteByEmail(Map<String, String> payload) {
        deleteUserService.deleteUserByEmail(payload);
        return ResponseEntity.ok().build();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteByUsername(Map<String, String> payload) {
        deleteUserService.deleteUserByUsername(payload);
        return ResponseEntity.ok().build();
    }
}