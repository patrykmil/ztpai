package iu.iu.spring_app.api.components.service;

import iu.iu.spring_app.api.components.model.Component;
import iu.iu.spring_app.api.components.model.Set;
import iu.iu.spring_app.api.components.model.Type;
import iu.iu.spring_app.api.components.repository.ComponentRepository;
import iu.iu.spring_app.api.components.repository.SetRepository;
import iu.iu.spring_app.api.components.repository.TypeRepository;
import iu.iu.spring_app.api.errors.ResourceNotFoundException;
import iu.iu.spring_app.api.users.model.User;
import iu.iu.spring_app.api.users.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddComponentService {
    private final ComponentRepository componentRepository;
    private final SetRepository setRepository;
    private final TypeRepository typeRepository;
    private final UserRepository userRepository;
    private final TagsService tagsService;
    private final ColorService colorService;
    private final ValidationService validationService;

    public AddComponentService(ComponentRepository componentRepository,
                               SetRepository setRepository,
                               TypeRepository typeRepository,
                               UserRepository userRepository,
                               TagsService tagsService,
                               ColorService colorService,
                               ValidationService validationService) {
        this.componentRepository = componentRepository;
        this.setRepository = setRepository;
        this.typeRepository = typeRepository;
        this.userRepository = userRepository;
        this.tagsService = tagsService;
        this.colorService = colorService;
        this.validationService = validationService;
    }

    @Transactional
    public Component addComponent(Component payload, String userEmail) {
        validationService.validateComponent(payload);

        User author = userRepository.findByEmail(validationService.sanitizeInput(userEmail))
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Type type = typeRepository.findByName(validationService.sanitizeInput(payload.getType().getName()))
                .orElseThrow(() -> new ResourceNotFoundException("Type not found"));

        Set set = setRepository.findByName(validationService.sanitizeInput(payload.getSet().getName()))
                .orElseThrow(() -> new ResourceNotFoundException("Set not found"));

        Component component = Component.builder()
                .name(validationService.sanitizeInput(payload.getName()))
                .html(validationService.sanitizeHtml(payload.getHtml()))
                .css(validationService.sanitizeCss(payload.getCss()))
                .author(author)
                .type(type)
                .build();
        component.setSet(set);

        colorService.setComponentColor(component, payload);
        tagsService.setComponentTags(component, payload);

        return componentRepository.save(component);
    }
}