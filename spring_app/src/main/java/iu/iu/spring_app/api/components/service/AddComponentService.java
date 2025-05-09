package iu.iu.spring_app.api.components.service;

import iu.iu.spring_app.api.components.model.Component;
import iu.iu.spring_app.api.components.model.Set;
import iu.iu.spring_app.api.components.model.Type;
import iu.iu.spring_app.api.components.repository.ComponentRepository;
import iu.iu.spring_app.api.components.repository.SetRepository;
import iu.iu.spring_app.api.components.repository.TypeRepository;
import iu.iu.spring_app.api.users.model.User;
import iu.iu.spring_app.api.users.repository.UserRepository;
import iu.iu.spring_app.api.users.service.GetUserService;
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
    private final GetUserService getUserService;
    private final TypeService typeService;
    private final SetService setService;

    public AddComponentService(ComponentRepository componentRepository,
                               SetRepository setRepository,
                               TypeRepository typeRepository,
                               UserRepository userRepository,
                               TagsService tagsService,
                               ColorService colorService,
                               ValidationService validationService,
                               GetUserService getUserService, TypeService typeService, SetService setService) {
        this.componentRepository = componentRepository;
        this.setRepository = setRepository;
        this.typeRepository = typeRepository;
        this.userRepository = userRepository;
        this.tagsService = tagsService;
        this.colorService = colorService;
        this.validationService = validationService;
        this.getUserService = getUserService;
        this.typeService = typeService;
        this.setService = setService;
    }

    @Transactional
    public Component addComponent(Component payload, String email) {
        validationService.validateComponent(payload);

        User author = getUserService.getUserByEmail(email);

        Type type = typeService.getTypeByName(validationService.sanitizeInput(payload.getType().getName()));

        Set set = setService.getSetByName(validationService.sanitizeInput(payload.getSet().getName()));

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