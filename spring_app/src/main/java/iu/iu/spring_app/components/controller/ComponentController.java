package iu.iu.spring_app.components.controller;

import iu.iu.spring_app.components.model.Component;
import iu.iu.spring_app.components.service.AddComponentService;
import iu.iu.spring_app.components.service.GetComponentService;
import iu.iu.spring_app.errors.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/components")
public class ComponentController {
    private final GetComponentService getComponentService;
    private final AddComponentService addComponentService;

    public ComponentController(GetComponentService getComponentService, AddComponentService addComponentService) {
        this.getComponentService = getComponentService;
        this.addComponentService = addComponentService;
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<Component>> getAllComponents() {
        List<Component> components = getComponentService.getAllComponents();
        if (components.isEmpty()) {
            throw new ResourceNotFoundException("No components found");
        }
        return ResponseEntity.ok(getComponentService.getAllComponents());
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
    public ResponseEntity<Component> addComponent(@RequestBody Component request) {
        if (request == null) {
            throw new ResourceNotFoundException("Request body is null");
        }
        Component savedComponent = addComponentService.addComponent(request);
        return ResponseEntity.created(URI.create("/api/components/" + savedComponent.getId()))
                .body(savedComponent);
    }
}