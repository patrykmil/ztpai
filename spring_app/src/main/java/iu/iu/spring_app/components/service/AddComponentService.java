package iu.iu.spring_app.components.service;

import iu.iu.spring_app.components.model.*;
import iu.iu.spring_app.components.repository.*;
import iu.iu.spring_app.errors.ResourceNotFoundException;
import iu.iu.spring_app.users.model.User;
import iu.iu.spring_app.users.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Service
public class AddComponentService {
    private final ComponentRepository componentRepository;
    private final TagRepository tagRepository;
    private final SetRepository setRepository;
    private final ColorRepository colorRepository;
    private final TypeRepository typeRepository;
    private final UserRepository userRepository;

    public AddComponentService(ComponentRepository componentRepository,
                               SetRepository setRepository,
                               ColorRepository colorRepository,
                               TagRepository tagRepository,
                               TypeRepository typeRepository, UserRepository userRepository) {
        this.componentRepository = componentRepository;
        this.setRepository = setRepository;
        this.tagRepository = tagRepository;
        this.colorRepository = colorRepository;
        this.typeRepository = typeRepository;
        this.userRepository = userRepository;
    }


    @Transactional
    public Component addComponent(Component request) {
        Component component = new Component();
        component.setName(request.getName());
        component.setHtml(request.getHtml());
        component.setCss(request.getCss());

        User author = userRepository.findByUsername(request.getAuthor().getUsername());
        component.setAuthor(author);

        Type type = typeRepository.findByName(request.getType().getName());
        if (type == null) {
            throw(new ResourceNotFoundException("Type " + request.getType().getName() + " not found"));
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
            Tag tag;
            for (Tag requestTag : request.getTags()) {
                tag = tagRepository.findByName(requestTag.getName());
                if (tag == null) {
                    throw new ResourceNotFoundException("Tag " + requestTag.getName() + " not found");
                }
                tags.add(tag);
            }
            component.setTags(tags);
        }

        Component savedComponent = componentRepository.save(component);

        return savedComponent;
    }
}