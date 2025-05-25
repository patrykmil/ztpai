package iu.iu.spring_app.api.components.service;

import iu.iu.spring_app.api.components.model.Component;
import iu.iu.spring_app.api.components.model.Set;
import iu.iu.spring_app.api.components.model.Type;
import iu.iu.spring_app.api.components.repository.ComponentRepository;
import iu.iu.spring_app.api.messages.model.Message;
import iu.iu.spring_app.api.messages.service.AddMessageService;
import iu.iu.spring_app.api.users.model.User;
import iu.iu.spring_app.api.users.service.GetUserService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddComponentService {
    private final ComponentRepository componentRepository;
    private final TagsService tagsService;
    private final ColorService colorService;
    private final ValidationService validationService;
    private final GetUserService getUserService;
    private final TypeService typeService;
    private final SetService setService;
    private final AddMessageService addMessageService;

    public AddComponentService(ComponentRepository componentRepository,
                               TagsService tagsService,
                               ColorService colorService,
                               ValidationService validationService,
                               GetUserService getUserService,
                               TypeService typeService,
                               SetService setService,
                               AddMessageService addMessageService) {
        this.componentRepository = componentRepository;
        this.tagsService = tagsService;
        this.colorService = colorService;
        this.validationService = validationService;
        this.getUserService = getUserService;
        this.typeService = typeService;
        this.setService = setService;
        this.addMessageService = addMessageService;
    }

    @Transactional
    public Component addComponent(Component payload, String email) {
        validationService.validateComponent(payload);

        User author = getUserService.getUserByEmail(email);

        Type type = typeService.getTypeByName(validationService.sanitizeInput(payload.getType().getName()));

        Set set = setService.getSetByName(validationService.sanitizeInput(payload.getSet().getName()), author);
        if(!set.getUser().getEmail().equals(email)) {
            throw new AccessDeniedException("Not allowed to use this set");
        }


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

        Component savedComponent = componentRepository.save(component);
        var message = Message.builder()
                                .title("New component added \"" + savedComponent.getName()+ "\"")
                                .link("/components/" + savedComponent.getId())
                                .user(savedComponent.getAuthor())
                                .build();
        addMessageService.addMessage(message);

        return savedComponent;
    }
}