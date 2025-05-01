package iu.iu.spring_app.api.components.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import iu.iu.spring_app.api.components.model.Set;
import iu.iu.spring_app.api.components.model.Tag;
import iu.iu.spring_app.api.components.model.Type;
import iu.iu.spring_app.api.components.service.SetService;
import iu.iu.spring_app.api.components.service.TagsService;
import iu.iu.spring_app.api.components.service.TypeService;
import iu.iu.spring_app.api.errors.ExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Component Properties", description = "APIs for managing component sets, tags and types")
public class ComponentPropertiesController {
    private final SetService setService;
    private final TypeService typeService;
    private final TagsService tagsService;

    public ComponentPropertiesController(SetService setService, TypeService typeService, TagsService tagsService) {
        this.setService = setService;
        this.typeService = typeService;
        this.tagsService = tagsService;
    }

    @Operation(summary = "Get all available tags", description = "Retrieves all component tags in the system")
    @ApiResponse(responseCode = "200", description = "Tags found", content = @Content(schema = @Schema(implementation = Tag.class)))
    @ApiResponse(responseCode = "404", description = "No tags found", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @GetMapping("/tags/get/all")
    public ResponseEntity<List<Tag>> getAllTags() {
        return ResponseEntity.ok(tagsService.getTags());
    }

    @Operation(summary = "Get all component types", description = "Retrieves all available component types")
    @ApiResponse(responseCode = "200", description = "Types found", content = @Content(schema = @Schema(implementation = Type.class)))
    @ApiResponse(responseCode = "404", description = "No types found", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @GetMapping("/types/get/all")
    public ResponseEntity<List<Type>> getAllTypes() {
        return ResponseEntity.ok(typeService.getTypes());
    }

    @Operation(summary = "Get all sets for a user", description = "Retrieves all component sets belonging to the specified user")
    @ApiResponse(responseCode = "200", description = "Sets found", content = @Content(schema = @Schema(implementation = Set.class)))
    @ApiResponse(responseCode = "404", description = "No sets found for user", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @GetMapping("/sets/get/{userId}")
    public ResponseEntity<List<Set>> getAllSets(@PathVariable Integer userId) {
        List<Set> sets = setService.getUserSets(userId);
        return ResponseEntity.ok(sets);
    }

    @Operation(summary = "Add a new set", description = "Creates a new component set for the authenticated user")
    @ApiResponse(responseCode = "201", description = "Set created successfully")
    @ApiResponse(responseCode = "403", description = "User not authenticated", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Set name", required = true,
            content = @Content(schema = @Schema(example = "{\"setName\": \"string\"}")))
    @PostMapping("/sets/add")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Set> addSet(@RequestBody Map<String, String> payload, Authentication authentication) {
        Set newSet = setService.addUserSet(payload.get("setName"), authentication.getName());
        return ResponseEntity.ok(newSet);
    }
}
