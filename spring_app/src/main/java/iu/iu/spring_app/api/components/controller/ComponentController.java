package iu.iu.spring_app.api.components.controller;

import iu.iu.spring_app.api.components.model.Component;
import iu.iu.spring_app.api.components.model.ComponentFilter;
import iu.iu.spring_app.api.components.service.AddComponentService;
import iu.iu.spring_app.api.components.service.DeleteComponentService;
import iu.iu.spring_app.api.components.service.GetComponentService;
import iu.iu.spring_app.api.components.service.ModifyComponentService;
import iu.iu.spring_app.api.errors.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@io.swagger.v3.oas.annotations.tags.Tag(name = "Components", description = "APIs for managing components")
public class ComponentController implements ComponentControllerInterface {
    private final GetComponentService getComponentService;
    private final AddComponentService addComponentService;
    private final ModifyComponentService modifyComponentService;
    private final DeleteComponentService deleteComponentService;

    public ComponentController(GetComponentService getComponentService,
                               AddComponentService addComponentService,
                               ModifyComponentService modifyComponentService,
                               DeleteComponentService deleteComponentService) {
        this.getComponentService = getComponentService;
        this.addComponentService = addComponentService;
        this.modifyComponentService = modifyComponentService;
        this.deleteComponentService = deleteComponentService;
    }

    @Override
    public ResponseEntity<Page<Component>> getAllComponents(int page, int size, String sort) {
        return ResponseEntity.ok(getComponentService.getAllComponents(page, size, sort));
    }

    @Override
    public ResponseEntity<List<Component>> getLikedComponents(String username) {
        return ResponseEntity.ok(getComponentService.getLikedByUserComponents(username));
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
    public ResponseEntity<Component> modifyComponent(Component payload, Authentication authentication) {
        if (payload == null) {
            throw new ResourceNotFoundException("Component cannot be null");
        }
        return ResponseEntity.ok(modifyComponentService.modifyComponent(payload, authentication.getName()));
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