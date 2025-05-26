package iu.iu.spring_app.api.users.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import iu.iu.spring_app.api.errors.ExceptionResponse;
import iu.iu.spring_app.api.users.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/api/users")
public interface UserControllerInterface {
    @Operation(summary = "Get all users", description = "Retrieves all users in the system")
    @ApiResponse(responseCode = "200", description = "Users found", content = @Content(schema = @Schema(implementation = User.class)))
    @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "No users found", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @GetMapping
    ResponseEntity<List<User>> getAllUsers();

    @Operation(summary = "Get user by ID", description = "Retrieves a specific user by their ID")
    @ApiResponse(responseCode = "200", description = "User found", content = @Content(schema = @Schema(implementation = User.class)))
    @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @GetMapping("/{id}")
    ResponseEntity<User> getUserById(@PathVariable Integer id);

    @Operation(summary = "Get user by email", description = "Retrieves a specific user by their email")
    @ApiResponse(responseCode = "200", description = "User found", content = @Content(schema = @Schema(implementation = User.class)))
    @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @PostMapping("/email")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Email to search", required = true,
            content = @Content(schema = @Schema(example = "{\"email\": \"user@iu.iu\"}")))
    ResponseEntity<User> getUserByEmail(@RequestBody Map<String, String> payload);

    @Operation(summary = "Get user by username", description = "Retrieves a specific user by their username")
    @ApiResponse(responseCode = "200", description = "User found", content = @Content(schema = @Schema(implementation = User.class)))
    @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @PostMapping("/username")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Username to search", required = true,
            content = @Content(schema = @Schema(example = "{\"username\": \"patryk\"}")))
    ResponseEntity<User> getUserByUsername(@RequestBody Map<String, String> payload);

    @Operation(summary = "Delete user by email", description = "Deletes a specific user by their email")
    @ApiResponse(responseCode = "200", description = "User deleted successfully")
    @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @DeleteMapping("/email")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Email of user to delete", required = true,
            content = @Content(schema = @Schema(example = "{\"email\": \"user@iu.iu\"}")))
    ResponseEntity<?> deleteByEmail(@RequestBody Map<String, String> payload);

    @Operation(summary = "Delete user by username", description = "Deletes a specific user by their username")
    @ApiResponse(responseCode = "200", description = "User deleted successfully")
    @ApiResponse(responseCode = "403", description = "Access denied", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @DeleteMapping("/username")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Username of user to delete", required = true,
            content = @Content(schema = @Schema(example = "{\"username\": \"patryk\"}")))
    ResponseEntity<?> deleteByUsername(@RequestBody Map<String, String> payload);
}