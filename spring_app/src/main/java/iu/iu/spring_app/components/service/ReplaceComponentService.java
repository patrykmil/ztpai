package iu.iu.spring_app.components.service;

import iu.iu.spring_app.components.model.Color;
import iu.iu.spring_app.components.model.Component;
import iu.iu.spring_app.components.model.Tag;
import iu.iu.spring_app.components.repository.ColorRepository;
import iu.iu.spring_app.components.repository.ComponentRepository;
import iu.iu.spring_app.components.repository.TagRepository;
import iu.iu.spring_app.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class ReplaceComponentService {
    private final ComponentRepository componentRepository;
    private final GetComponentService getComponentService;
    private final ColorRepository colorRepository;
    private final TagRepository tagRepository;

    public ReplaceComponentService(ComponentRepository componentRepository,
                                   GetComponentService getComponentService,
                                   ColorRepository colorRepository,
                                   TagRepository tagRepository) {
        this.componentRepository = componentRepository;
        this.getComponentService = getComponentService;
        this.colorRepository = colorRepository;
        this.tagRepository = tagRepository;
    }
    public Component replaceComponent(Component request) {
        Component oldComponent = getComponentService.getComponentById(request.getId());
        if (oldComponent == null) {
            throw new ResourceNotFoundException("Component " + request.getId() + " not found");
        }
        oldComponent.setName(request.getName());
        oldComponent.setHtml(request.getHtml());
        oldComponent.setCss(request.getCss());
        if (request.getColor() != null) {
            Color color = colorRepository.findByHex(request.getColor().getHex().toUpperCase());
            if (color == null) {
                color = new Color();
                color.setHex(request.getColor().getHex());
                color = colorRepository.save(color);
            }
            oldComponent.setColor(color);
        }
        if (request.getTags() != null) {
            java.util.Set<Tag> tags = new HashSet<>();
            Tag tag;
            for (Tag requestTag : request.getTags()) {
                tag = tagRepository.findByName(requestTag.getName());
                if (tag == null) {
                    throw new ResourceNotFoundException("Tag " + requestTag.getName() + " not found");
                }
                tags.add(tag);
            }
            oldComponent.setTags(tags);
        }

        Component savedComponent = componentRepository.save(oldComponent);

        return savedComponent;
    }

}
