package iu.iu.spring_app.controller;

import iu.iu.spring_app.model.*;
import iu.iu.spring_app.repository.*;
import jakarta.persistence.EntityManager;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ComponentController {
    private final ComponentRepository componentRepository;
    private final TagRepository tagRepository;
    private final SetRepository setRepository;
    private final ColorRepository colorRepository;
    private final TypeRepository typeRepository;
    private final EntityManager entityManager;

    public ComponentController(ComponentRepository componentRepository, SetRepository setRepository, ColorRepository colorRepository, TagRepository tagRepository, EntityManager entityManager, TypeRepository typeRepository) {
        this.componentRepository = componentRepository;
        this.setRepository = setRepository;
        this.tagRepository = tagRepository;
        this.colorRepository = colorRepository;
        this.typeRepository = typeRepository;
        this.entityManager = entityManager;
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

    @PostMapping("/addComponent")
    @Transactional
    public ResponseEntity<Component> addComponent(@RequestBody Component request) {

        Integer id = -1;
        try {
            Set set = setRepository.findByName(request.getSetName());
            if (set == null) {
                System.out.println(request.getSetName());
                return ResponseEntity.notFound().build();
            }

            Color color = colorRepository.findByHex(request.getHex());
            if (color == null) {
                System.out.println(request.getHex());
                return ResponseEntity.notFound().build();
            }

            Type type = typeRepository.findByName(request.getTypeName());
            if (type == null) {
                System.out.println(request.getTypeName());
                return ResponseEntity.notFound().build();
            }

            List<Tag> tags = new ArrayList<>();
            List<String> tagsString = List.of(request.getTags().split("\\s*,\\s*"));

            for (String tag : tagsString) {
                Tag t = tagRepository.findByName(tag);
                if (t == null) {
                    System.out.println(tag);
                    return ResponseEntity.notFound().build();
                }
                tags.add(t);
            }

            id = componentRepository.addComponent(request, set, color, type);
            componentRepository.addTags(id, tags);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return componentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
