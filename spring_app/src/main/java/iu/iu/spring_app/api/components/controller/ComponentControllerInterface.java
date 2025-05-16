package iu.iu.spring_app.api.components.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import iu.iu.spring_app.api.components.model.Component;
import iu.iu.spring_app.api.components.model.ComponentFilter;
import iu.iu.spring_app.api.errors.ExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/components")
public interface ComponentControllerInterface {
    @Operation(summary = "Get all components", description = "Retrieves all components in the system")
    @ApiResponse(responseCode = "200", description = "Components found", content = @Content(schema = @Schema(implementation = Component.class)))
    @GetMapping("/get/all")
    ResponseEntity<List<Component>> getAllComponents();

    @Operation(summary = "Get component by ID", description = "Retrieves a specific component by its ID")
    @ApiResponse(responseCode = "200", description = "Component found", content = @Content(schema = @Schema(implementation = Component.class)))
    @ApiResponse(responseCode = "404", description = "Component not found", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @GetMapping("/get/{id}")
    ResponseEntity<Component> getComponent(@PathVariable Integer id);

    @Operation(summary = "Add a new component", description = "Creates a new component for author")
    @ApiResponse(responseCode = "201", description = "Component created successfully", content = @Content(schema = @Schema(implementation = Component.class)))
    @ApiResponse(responseCode = "403", description = "User not authenticated", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "Unable to create component", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @PostMapping("/add")
    ResponseEntity<Component> addComponent(@RequestBody Component payload, Authentication authentication);

    @Operation(summary = "Replace existing component", description = "Updates an existing component for author")
    @ApiResponse(responseCode = "200", description = "Component updated successfully", content = @Content(schema = @Schema(implementation = Component.class)))
    @ApiResponse(responseCode = "403", description = "User not authenticated", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "Component not found", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @PutMapping("/replace")
    ResponseEntity<Component> replaceComponent(@RequestBody Component payload, Authentication authentication);

    @Operation(summary = "Delete component", description = "Deletes an existing component for author")
    @ApiResponse(responseCode = "200", description = "Component deleted successfully")
    @ApiResponse(responseCode = "403", description = "User not authenticated", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "Component not found", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @DeleteMapping("/delete/{id}")
    ResponseEntity<Component> deleteComponent(@PathVariable Integer id, Authentication authentication);

    @Operation(summary = "Get filtered components", description = "Retrieves all components meeting the filtering conditions")
    @ApiResponse(responseCode = "200", description = "Components found", content = @Content(schema = @Schema(implementation = Component.class)))
    @PostMapping("/search")
    ResponseEntity<List<Component>> searchComponents(@RequestBody ComponentFilter payload);

    @Operation(summary = "Get components liked by user", description = "Retrieves all components liked by user with provided ID")
    @ApiResponse(responseCode = "200", description = "Components found", content = @Content(schema = @Schema(implementation = Component.class)))
    @GetMapping("/get/liked/{userId}")
    ResponseEntity<List<Component>> getLikedComponents(@PathVariable String username);

    @Operation(summary = "Get components from set", description = "Retrieves all components from set provided with setID")
    @ApiResponse(responseCode = "200", description = "Components found", content = @Content(schema = @Schema(implementation = Component.class)))
    @GetMapping("/get/set/{setId}")
    ResponseEntity<List<Component>> getSetComponents(@PathVariable Integer setId);
}