package iu.iu.spring_app.components.controller;

import iu.iu.spring_app.components.model.Component;
import iu.iu.spring_app.components.service.AddComponentService;
import iu.iu.spring_app.components.service.DeleteComponentService;
import iu.iu.spring_app.components.service.GetComponentService;
import iu.iu.spring_app.components.service.ReplaceComponentService;
import iu.iu.spring_app.errors.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/components")
public class ComponentController {
    private final GetComponentService getComponentService;
    private final AddComponentService addComponentService;
    private final ReplaceComponentService replaceComponentService;
    private final DeleteComponentService deleteComponentService;

    public ComponentController(GetComponentService getComponentService,
                               AddComponentService addComponentService,
                               ReplaceComponentService replaceComponentService, DeleteComponentService deleteComponentService) {
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
    public ResponseEntity<Component> addComponent(@RequestBody Component request) {
        if (request == null) {
            throw new ResourceNotFoundException("Request body is null");
        }
        Component savedComponent = addComponentService.addComponent(request);
        return ResponseEntity.created(URI.create("/api/components/" + savedComponent.getId()))
                .body(savedComponent);
    }

    @PutMapping("/replace")
    public ResponseEntity<Component> replaceComponent(@RequestBody Component request) {
        if (request == null) {
            throw new ResourceNotFoundException("Request body is null");
        }
        return ResponseEntity.ok(replaceComponentService.replaceComponent(request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Component> deleteComponent(@RequestBody Map<String, Integer> request) {
        if (request == null) {
            throw new ResourceNotFoundException("Request body is null");
        }
        deleteComponentService.deleteComponentById(request);
        return ResponseEntity.ok().build();
    }
}