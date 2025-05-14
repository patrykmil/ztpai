package iu.iu.spring_app.api.components.controller;

import iu.iu.spring_app.api.components.model.Component;
import iu.iu.spring_app.api.components.model.ComponentFilter;
import iu.iu.spring_app.api.components.service.AddComponentService;
import iu.iu.spring_app.api.components.service.DeleteComponentService;
import iu.iu.spring_app.api.components.service.GetComponentService;
import iu.iu.spring_app.api.components.service.ReplaceComponentService;
import iu.iu.spring_app.api.errors.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@io.swagger.v3.oas.annotations.tags.Tag(name = "Components", description = "APIs for managing components")
public class ComponentController implements ComponentControllerInterface {
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

    @Override
    public ResponseEntity<List<Component>> getAllComponents() {
        return ResponseEntity.ok(getComponentService.getAllComponents());
    }

    @Override
    public ResponseEntity<List<Component>> getLikedComponents(Integer userId) {
        return ResponseEntity.ok(getComponentService.getLikedByUserComponents(userId));
    }

    @Override
    public ResponseEntity<Component> getComponent(Integer id) {
        return ResponseEntity.ok(getComponentService.getComponentById(id));
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Component> addComponent(Component payload, Authentication authentication) {
        if (payload == null) {
            throw new ResourceNotFoundException("Component cannot be null");
        }
        Component savedComponent = addComponentService.addComponent(payload, authentication.getName());
        return ResponseEntity.created(URI.create("/api/components/" + savedComponent.getId()))
                .body(savedComponent);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Component> replaceComponent(Component payload, Authentication authentication) {
        if (payload == null) {
            throw new ResourceNotFoundException("Component cannot be null");
        }
        return ResponseEntity.ok(replaceComponentService.replaceComponent(payload, authentication.getName()));
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Component> deleteComponent(Integer id, Authentication authentication) {
        deleteComponentService.deleteComponentById(id, authentication.getName());
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<Component>> searchComponents(ComponentFilter payload) {
        if (payload == null) {
            throw new ResourceNotFoundException("Payload cannot be null");
        }
        return ResponseEntity.ok(getComponentService.getFilteredComponents(payload));
    }

    @Override
    public ResponseEntity<List<Component>> getSetComponents(Integer setId) {
        return ResponseEntity.ok(getComponentService.getSetComponents(setId));
    }
}