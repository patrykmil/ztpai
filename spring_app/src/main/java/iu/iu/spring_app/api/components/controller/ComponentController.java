package iu.iu.spring_app.api.components.controller;

import iu.iu.spring_app.api.components.model.Component;
import iu.iu.spring_app.api.components.service.AddComponentService;
import iu.iu.spring_app.api.components.service.DeleteComponentService;
import iu.iu.spring_app.api.components.service.GetComponentService;
import iu.iu.spring_app.api.components.service.ReplaceComponentService;
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

    @GetMapping("/get/all")
    public ResponseEntity<List<Component>> getAllComponents() {
        List<Component> components = getComponentService.getAllComponents();
        if (components.isEmpty()) {
            throw new ResourceNotFoundException("No components found");
        }
        return ResponseEntity.ok(components);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Component> getComponent(@PathVariable Integer id) {
        Component component = getComponentService.getComponentById(id);
        if (component == null) {
            throw new ResourceNotFoundException("Component " + id + " not found");
        }
        return ResponseEntity.ok(component);
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Component> addComponent(@RequestBody Component request,
                                                  Authentication authentication) {
        if (request == null) {
            throw new ResourceNotFoundException("Request body is null");
        }
        Component savedComponent = addComponentService.addComponent(request, authentication.getName());
        return ResponseEntity.created(URI.create("/api/components/" + savedComponent.getId()))
                .body(savedComponent);
    }

    @PutMapping("/replace")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Component> replaceComponent(@RequestBody Component request,
                                                      Authentication authentication) {
        if (request == null) {
            throw new ResourceNotFoundException("Request body is null");
        }
        return ResponseEntity.ok(replaceComponentService.replaceComponent(request, authentication.getName()));
    }

    @DeleteMapping("/delete")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Component> deleteComponent(@RequestBody Map<String, Integer> request,
                                                     Authentication authentication) {
        if (request == null) {
            throw new ResourceNotFoundException("Request body is null");
        }
        deleteComponentService.deleteComponentById(request.get("id"), authentication.getName());
        return ResponseEntity.ok().build();
    }
}