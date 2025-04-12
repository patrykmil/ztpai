package iu.iu.spring_app.components.service;

import iu.iu.spring_app.components.model.Component;
import iu.iu.spring_app.components.model.Tag;
import iu.iu.spring_app.components.repository.TagRepository;
import iu.iu.spring_app.errors.ResourceNotFoundException;
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
                Tag tag = tagRepository.findByName(requestTag.getName());
                if (tag == null) {
                    throw new ResourceNotFoundException("Tag " + requestTag.getName() + " not found");
                }
                tags.add(tag);
            }
            component.setTags(tags);
        }
    }
}
