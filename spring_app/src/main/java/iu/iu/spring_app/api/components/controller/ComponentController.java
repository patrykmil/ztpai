package iu.iu.spring_app.api.components.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import iu.iu.spring_app.api.components.model.Component;
import iu.iu.spring_app.api.components.service.AddComponentService;
import iu.iu.spring_app.api.components.service.DeleteComponentService;
import iu.iu.spring_app.api.components.service.GetComponentService;
import iu.iu.spring_app.api.components.service.ReplaceComponentService;
import iu.iu.spring_app.api.errors.ExceptionResponse;
import iu.iu.spring_app.api.errors.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/components")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Components", description = "APIs for managing components")
public class ComponentController {
    private final GetComponentService getComponentService;
    private final AddComponentService addComponentService;
    private final ReplaceComponentService replaceComponentService;
    private final DeleteComponentService deleteComponentService;

    public ComponentController(GetComponentService getComponentService,
                               AddComponentService addComponentService,
                               ReplaceComponentService replaceComponentService,
                               DeleteComponentService deleteComponentService) {
        this.getComponentService = getComponentService;
        this.addComponentService = addComponentService;
        this.replaceComponentService = replaceComponentService;
        this.deleteComponentService = deleteComponentService;
    }

    @Operation(summary = "Get all components", description = "Retrieves all components in the system")
    @ApiResponse(responseCode = "200", description = "Components found", content = @Content(schema = @Schema(implementation = Component.class)))
    @ApiResponse(responseCode = "404", description = "No components found", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @GetMapping("/get/all")
    public ResponseEntity<List<Component>> getAllComponents() {
        List<Component> components = getComponentService.getAllComponents();
        if (components.isEmpty()) {
            throw new ResourceNotFoundException("No components found");
        }
        return ResponseEntity.ok(components);
    }

    @Operation(summary = "Get component by ID", description = "Retrieves a specific component by its ID")
    @ApiResponse(responseCode = "200", description = "Component found", content = @Content(schema = @Schema(implementation = Component.class)))
    @ApiResponse(responseCode = "404", description = "Component not found", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @GetMapping("/get/{id}")
    public ResponseEntity<Component> getComponent(@PathVariable Integer id) {
        Component component = getComponentService.getComponentById(id);
        if (component == null) {
            throw new ResourceNotFoundException("Component " + id + " not found");
        }
        return ResponseEntity.ok(component);
    }

    @Operation(summary = "Add a new component", description = "Creates a new component for the authenticated user")
    @ApiResponse(responseCode = "201", description = "Component created successfully", content = @Content(schema = @Schema(implementation = Component.class)))
    @ApiResponse(responseCode = "403", description = "User not authenticated", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "Unable to create component", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Component> addComponent(@RequestBody Component payload,
                                                  Authentication authentication) {
        if (payload == null) {
            throw new ResourceNotFoundException("Request body is null");
        }
        Component savedComponent = addComponentService.addComponent(payload, authentication.getName());
        return ResponseEntity.created(URI.create("/api/components/" + savedComponent.getId()))
                .body(savedComponent);
    }

    @Operation(summary = "Replace existing component", description = "Updates an existing component for the authenticated user")
    @ApiResponse(responseCode = "200", description = "Component updated successfully", content = @Content(schema = @Schema(implementation = Component.class)))
    @ApiResponse(responseCode = "403", description = "User not authenticated", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "Component not found", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @PutMapping("/replace")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Component> replaceComponent(@RequestBody Component payload,
                                                      Authentication authentication) {
        if (payload == null) {
            throw new ResourceNotFoundException("Request body is null");
        }
        return ResponseEntity.ok(replaceComponentService.replaceComponent(payload, authentication.getName()));
    }

    @Operation(summary = "Delete component", description = "Deletes an existing component for the authenticated user")
    @ApiResponse(responseCode = "200", description = "Component deleted successfully")
    @ApiResponse(responseCode = "403", description = "User not authenticated", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "Component not found", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @DeleteMapping("/delete")
    @PreAuthorize("isAuthenticated()")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Component ID to delete", required = true,
            content = @Content(schema = @Schema(example = "{\"id\": 1}")))
    public ResponseEntity<Component> deleteComponent(@RequestBody Map<String, Integer> payload,
                                                     Authentication authentication) {
        if (payload == null) {
            throw new ResourceNotFoundException("Request body is null");
        }
        deleteComponentService.deleteComponentById(payload.get("id"), authentication.getName());
        return ResponseEntity.ok().build();
    }
}