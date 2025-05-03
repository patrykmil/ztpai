package iu.iu.spring_app.api.components.service;

import iu.iu.spring_app.api.components.model.Component;
import iu.iu.spring_app.api.components.model.Tag;
import iu.iu.spring_app.api.components.repository.TagRepository;
import iu.iu.spring_app.api.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TagsService {
    private final TagRepository tagRepository;
    private final ValidationService validationService;

    public TagsService(TagRepository tagRepository, ValidationService validationService) {
        this.tagRepository = tagRepository;
        this.validationService = validationService;
    }

    public void setComponentTags(Component component, Component payload) {
        if (payload.getTags() != null) {
            validationService.validateTags(payload.getTags());

            Set<Tag> tags = payload.getTags().stream()
                    .map(payloadTag -> validationService.sanitizeInput(payloadTag.getName()))
                    .map(this::getTagByName)
                    .collect(Collectors.toSet());

            component.setTags(tags);
        }
    }

    public List<Tag> getTags() {
        return  tagRepository.findAll();
    }

    public Tag getTagByName(String name) {
        return tagRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found"));
    }
}
