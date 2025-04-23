package iu.iu.spring_app.api.components.controller;

import iu.iu.spring_app.api.components.model.Set;
import iu.iu.spring_app.api.components.model.Tag;
import iu.iu.spring_app.api.components.model.Type;
import iu.iu.spring_app.api.components.service.SetService;
import iu.iu.spring_app.api.components.service.TagsService;
import iu.iu.spring_app.api.components.service.TypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AddComponentFormController {
    private final SetService setService;
    private final TypeService typeService;
    private final TagsService tagsService;

    public AddComponentFormController(SetService setService, TypeService typeService, TagsService tagsService) {
        this.setService = setService;
        this.typeService = typeService;
        this.tagsService = tagsService;
    }

    @GetMapping("/sets/get/{userId}")
    public ResponseEntity<List<Set>> getAllSets(@PathVariable Integer userId) {
    List<Set> sets = setService.getUserSets(userId);
        return ResponseEntity.ok(sets);
    }

    @GetMapping("/tags/get/all")
    public ResponseEntity<List<Tag>> getAllTags() {
        return ResponseEntity.ok(tagsService.getTags());
    }

    @GetMapping("/types/get/all")
    public ResponseEntity<List<Type>> getAllTypes() {
        return ResponseEntity.ok(typeService.getTypes());
    }

    @PostMapping("/sets/add")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Set> addSet(@RequestBody String setName, Authentication authentication) {
        setService.addUserSet(setName, authentication.getName());
        return ResponseEntity.ok().build();
    }
}
