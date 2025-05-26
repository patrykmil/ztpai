package iu.iu.spring_app.api.components.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import iu.iu.spring_app.api.components.model.Set;
import iu.iu.spring_app.api.components.model.Tag;
import iu.iu.spring_app.api.components.model.Type;
import iu.iu.spring_app.api.errors.ExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/api")
public interface ComponentPropertiesControllerInterface {
    @Operation(summary = "Get all available tags", description = "Retrieves all component tags in the system")
    @ApiResponse(responseCode = "200", description = "Tags found", content = @Content(schema = @Schema(implementation = Tag.class)))
    @ApiResponse(responseCode = "404", description = "No tags found", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @GetMapping("/tags")
    ResponseEntity<List<Tag>> getAllTags();

    @Operation(summary = "Get all component types", description = "Retrieves all available component types")
    @ApiResponse(responseCode = "200", description = "Types found", content = @Content(schema = @Schema(implementation = Type.class)))
    @ApiResponse(responseCode = "404", description = "No types found", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @GetMapping("/types")
    ResponseEntity<List<Type>> getAllTypes();

    @Operation(summary = "Get all sets for a user", description = "Retrieves all component sets belonging to user with specified id")
    @ApiResponse(responseCode = "200", description = "Sets found", content = @Content(schema = @Schema(implementation = Set.class)))
    @ApiResponse(responseCode = "404", description = "No sets found for user", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @GetMapping("/sets/users/id/{userId}")
    ResponseEntity<List<Set>> getAllSetsById(@PathVariable Integer userId);

    @Operation(summary = "Get all sets for a user", description = "Retrieves all component sets belonging to user with specified name")
    @ApiResponse(responseCode = "200", description = "Sets found", content = @Content(schema = @Schema(implementation = Set.class)))
    @ApiResponse(responseCode = "404", description = "No sets found for user", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @GetMapping("/sets/users/name/{username}")
    ResponseEntity<List<Set>> getAllSetsByName(@PathVariable String username);

    @Operation(summary = "Add a new set", description = "Creates a new component set for the authenticated user")
    @ApiResponse(responseCode = "201", description = "Set created successfully")
    @ApiResponse(responseCode = "403", description = "User not authenticated", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Set name", required = true,
            content = @Content(schema = @Schema(example = "{\"setName\": \"string\"}")))
    @PostMapping("/sets")
    ResponseEntity<Set> addSet(@RequestBody Map<String, String> payload, Authentication authentication);
}