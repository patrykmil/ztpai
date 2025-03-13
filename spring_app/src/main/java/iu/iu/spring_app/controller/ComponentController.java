package iu.iu.spring_app.controller;

import iu.iu.spring_app.model.Component;
import iu.iu.spring_app.repository.ComponentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ComponentController {
    private final ComponentRepository componentRepository;

    public ComponentController(ComponentRepository componentRepository) {
        this.componentRepository = componentRepository;
    }

    @GetMapping("/components")
    public ResponseEntity<List<Component>> getAllComponents() {
        return ResponseEntity.ok(componentRepository.findAll());

    }

    @GetMapping("/components/{id}")
    public ResponseEntity<Component> getComponent(@PathVariable Integer id) {
        return componentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
