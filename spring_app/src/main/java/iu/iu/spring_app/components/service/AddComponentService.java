package iu.iu.spring_app.components.service;

import iu.iu.spring_app.components.model.*;
import iu.iu.spring_app.components.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.HashSet;

@Service
public class AddComponentService {
    private final ComponentRepository componentRepository;
    private final TagRepository tagRepository;
    private final SetRepository setRepository;
    private final ColorRepository colorRepository;
    private final TypeRepository typeRepository;

    public AddComponentService(ComponentRepository componentRepository,
                               SetRepository setRepository,
                               ColorRepository colorRepository,
                               TagRepository tagRepository,
                               TypeRepository typeRepository) {
        this.componentRepository = componentRepository;
        this.setRepository = setRepository;
        this.tagRepository = tagRepository;
        this.colorRepository = colorRepository;
        this.typeRepository = typeRepository;
    }


    @Transactional
    public ResponseEntity<Component> addComponent(Component request) {
        Component component = new Component();
        component.setName(request.getName());
        component.setHtml(request.getHtml());
        component.setCss(request.getCss());
        component.setAuthor(request.getAuthor());

        Type type = typeRepository.findByName(request.getType().getName());
        if (type == null) {
            return ResponseEntity.badRequest().build();
        }
        component.setType(type);

        if (request.getColor() != null) {
            Color color = colorRepository.findByHex(request.getColor().getHex().toUpperCase());
            if (color == null) {
                color = new Color();
                color.setHex(request.getColor().getHex());
                color = colorRepository.save(color);
            }
            component.setColor(color);
        }

        if (request.getSet() != null && request.getSet().getName() != null) {
            Set set = setRepository.findByName(request.getSet().getName());
            component.setSet(set);
        }

        if (request.getTags() != null) {
            java.util.Set<Tag> tags = new HashSet<>();
            for (Tag requestTag : request.getTags()) {
                Tag tag = tagRepository.findByName(requestTag.getName());
                if (tag == null) {
                    tag = new Tag();
                    tag.setName(requestTag.getName());
                    tag = tagRepository.save(tag);
                }
                tags.add(tag);
            }
            component.setTags(tags);
        }

        Component savedComponent = componentRepository.save(component);

        return ResponseEntity.created(URI.create("/api/components/" + savedComponent.getId()))
                .body(savedComponent);
    }
}