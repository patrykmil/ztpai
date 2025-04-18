package iu.iu.spring_app.api.components.service;

import iu.iu.spring_app.api.components.model.Component;
import iu.iu.spring_app.api.components.model.Tag;
import iu.iu.spring_app.api.components.repository.TagRepository;
import iu.iu.spring_app.api.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TagsService {
    private final TagRepository tagRepository;

    public TagsService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public void setComponentTags(Component component, Component request) {
        if (request.getTags() != null) {
            Set<Tag> tags = new HashSet<>();
            for (Tag requestTag : request.getTags()) {
                Tag tag = tagRepository.findByName(requestTag.getName())
                        .orElseThrow(() -> new ResourceNotFoundException("Tag not found"));
                tags.add(tag);
            }
            component.setTags(tags);
        }
    }
}
