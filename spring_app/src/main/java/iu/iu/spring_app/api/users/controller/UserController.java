package iu.iu.spring_app.api.users.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import iu.iu.spring_app.api.errors.ExceptionResponse;
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
@io.swagger.v3.oas.annotations.tags.Tag(name = "Users", description = "APIs for managing users (Admin only)")
public class UserController {
    private final GetUserService getUserService;
    private final DeleteUserService deleteUserService;

    public UserController(GetUserService getUserService, DeleteUserService deleteUserService) {
        this.getUserService = getUserService;
        this.deleteUserService = deleteUserService;
    }

    @Operation(summary = "Get all users", description = "Retrieves all users in the system")
    @ApiResponse(responseCode = "200", description = "Users found", content = @Content(schema = @Schema(implementation = User.class)))
    @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "No users found", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @GetMapping("/get/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = getUserService.getAllUsers();
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No users found");
        }
        return ResponseEntity.ok(getUserService.getAllUsers());
    }

    @Operation(summary = "Get user by ID", description = "Retrieves a specific user by their ID")
    @ApiResponse(responseCode = "200", description = "User found", content = @Content(schema = @Schema(implementation = User.class)))
    @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        User user = getUserService.getUserById(id);
        if (user == null) {
            throw new ResourceNotFoundException("User " + id + " not found");
        }
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Get user by email", description = "Retrieves a specific user by their email")
    @ApiResponse(responseCode = "200", description = "User found", content = @Content(schema = @Schema(implementation = User.class)))
    @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @PostMapping("/get/email")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Email to search", required = true,
            content = @Content(schema = @Schema(example = "{\"email\": \"user@iu.iu\"}")))
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserByEmail(@RequestBody Map<String, String> payload) {
        User user = getUserService.getUserByEmail(payload);
        if (user == null) {
            throw new ResourceNotFoundException("User " + payload.get("email") + " not found");
        }
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Get user by username", description = "Retrieves a specific user by their username")
    @ApiResponse(responseCode = "200", description = "User found", content = @Content(schema = @Schema(implementation = User.class)))
    @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Username to search", required = true,
            content = @Content(schema = @Schema(example = "{\"username\": \"patryk\"}")))
    @PostMapping("/get/by/username")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserByUsername(@RequestBody Map<String, String> payload) {
        User user = getUserService.getUserByUsername(payload);
        if (user == null) {
            throw new ResourceNotFoundException("User " + payload.get("username") + " not found");
        }
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Delete user by email", description = "Deletes a specific user by their email")
    @ApiResponse(responseCode = "200", description = "User deleted successfully")
    @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @DeleteMapping("/delete/email")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Email of user to delete", required = true,
            content = @Content(schema = @Schema(example = "{\"email\": \"user@iu.iu\"}")))
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteByEmail(@RequestBody Map<String, String> payload) {
        deleteUserService.deleteUserByEmail(payload);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete user by username", description = "Deletes a specific user by their username")
    @ApiResponse(responseCode = "200", description = "User deleted successfully")
    @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @DeleteMapping("/delete/username")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Username of user to delete", required = true,
            content = @Content(schema = @Schema(example = "{\"username\": \"patryk\"}")))
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteByUsername(@RequestBody Map<String, String> payload) {
        deleteUserService.deleteUserByUsername(payload);
        return ResponseEntity.ok().build();
    }
}