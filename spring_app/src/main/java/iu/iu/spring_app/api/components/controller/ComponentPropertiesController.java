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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@io.swagger.v3.oas.annotations.tags.Tag(name = "Component Properties", description = "APIs for managing component sets, tags and types")
public class ComponentPropertiesController implements ComponentPropertiesControllerInterface {
    private final SetService setService;
    private final TypeService typeService;
    private final TagsService tagsService;

    public ComponentPropertiesController(SetService setService, TypeService typeService, TagsService tagsService) {
        this.setService = setService;
        this.typeService = typeService;
        this.tagsService = tagsService;
    }

    @Override
    public ResponseEntity<List<Tag>> getAllTags() {
        return ResponseEntity.ok(tagsService.getTags());
    }

    @Override
    public ResponseEntity<List<Type>> getAllTypes() {
        return ResponseEntity.ok(typeService.getTypes());
    }

    @Override
    public ResponseEntity<List<Set>> getAllSets(Integer userId) {
        List<Set> sets = setService.getUserSets(userId);
        return ResponseEntity.ok(sets);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Set> addSet(Map<String, String> payload, Authentication authentication) {
        Set newSet = setService.addUserSet(payload.get("setName"), authentication.getName());
        return ResponseEntity.ok(newSet);
    }
}