package iu.iu.spring_app.api.components.service;

import iu.iu.spring_app.api.components.model.Component;
import iu.iu.spring_app.api.components.model.Set;
import iu.iu.spring_app.api.components.model.Type;
import iu.iu.spring_app.api.components.repository.ComponentRepository;
import iu.iu.spring_app.api.users.model.User;
import iu.iu.spring_app.api.users.service.GetUserService;
import jakarta.transaction.Transactional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;


@Service
public class ModifyComponentService {
    private final ComponentRepository componentRepository;
    private final GetComponentService getComponentService;
    private final TagsService tagsService;
    private final ColorService colorService;
    private final ValidationService validationService;
    private final TypeService typeService;
    private final SetService setService;
    private final GetUserService getUserService;

    public ModifyComponentService(ComponentRepository componentRepository,
                                  GetComponentService getComponentService,
                                  TagsService tagsService,
                                  ColorService colorService,
                                  ValidationService validationService, TypeService typeService, SetService setService, GetUserService getUserService) {
        this.componentRepository = componentRepository;
        this.getComponentService = getComponentService;
        this.tagsService = tagsService;
        this.colorService = colorService;
        this.validationService = validationService;
        this.typeService = typeService;
        this.setService = setService;
        this.getUserService = getUserService;
    }

    @Transactional
    public Component modifyComponent(Component payload, String email) {
        validationService.validateComponent(payload);

        User user = getUserService.getUserByEmail(email);

        Component oldComponent = getComponentService.getComponentById(payload.getId());

        Type type = typeService.getTypeByName(validationService.sanitizeInput(payload.getType().getName()));

        Set set = setService.getSetByName(validationService.sanitizeInput(payload.getSet().getName()), user);

        if (!set.getUser().getEmail().equals(email)) {
            throw new AccessDeniedException("Not allowed to use this set");
        }

        if (!oldComponent.getAuthor().getEmail().equals(email)) {
            throw new AccessDeniedException("Not authorized to modify this component");
        }

        oldComponent.setName(validationService.sanitizeInput(payload.getName()));
        oldComponent.setHtml(validationService.sanitizeHtml(payload.getHtml()));
        oldComponent.setCss(validationService.sanitizeCss(payload.getCss()));
        oldComponent.setType(type);
        oldComponent.setSet(set);


        colorService.setComponentColor(oldComponent, payload);
        tagsService.setComponentTags(oldComponent, payload);

        return componentRepository.save(oldComponent);
    }
}