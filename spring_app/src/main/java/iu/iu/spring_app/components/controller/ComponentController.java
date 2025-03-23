package iu.iu.spring_app.components.controller;

import iu.iu.spring_app.components.model.Component;
import iu.iu.spring_app.components.service.AddComponentService;
import iu.iu.spring_app.components.service.GetComponentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ComponentController {
    private final GetComponentService getComponentService;
    private final AddComponentService addComponentService;

    public ComponentController(GetComponentService getComponentService, AddComponentService addComponentService) {
        this.getComponentService = getComponentService;
        this.addComponentService = addComponentService;
    }

    @GetMapping("/components")
    public ResponseEntity<List<Component>> getAllComponents() {
        return ResponseEntity.ok(getComponentService.getAllComponents());
    }

    @GetMapping("/components/{id}")
    public ResponseEntity<Component> getComponent(@PathVariable Integer id) {
        Component component = getComponentService.getComponentById(id);
        return component != null ? ResponseEntity.ok(component) : ResponseEntity.notFound().build();
    }

    @PostMapping("/addComponent")
    public ResponseEntity<Component> addComponent(@RequestBody Component request) {
        return addComponentService.addComponent(request);
    }
}