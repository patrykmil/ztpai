package iu.iu.spring_app.api.components.service;

import iu.iu.spring_app.api.components.model.Component;
import iu.iu.spring_app.api.components.repository.ComponentRepository;
import iu.iu.spring_app.api.errors.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ReplaceComponentService {
    private final ComponentRepository componentRepository;
    private final GetComponentService getComponentService;
    private final TagsService tagsService;
    private final ColorService colorService;
    private final ValidationService validationService;

    public ReplaceComponentService(ComponentRepository componentRepository,
                                   GetComponentService getComponentService,
                                   TagsService tagsService,
                                   ColorService colorService,
                                   ValidationService validationService) {
        this.componentRepository = componentRepository;
        this.getComponentService = getComponentService;
        this.tagsService = tagsService;
        this.colorService = colorService;
        this.validationService = validationService;
    }

    @Transactional
    public Component replaceComponent(Component payload, String userEmail) {
        validationService.validateComponent(payload);

        Component oldComponent = getComponentService.getComponentById(payload.getId());
        if (oldComponent == null) {
            throw new ResourceNotFoundException("Component not found");
        }

        String sanitizedEmail = validationService.sanitizeInput(userEmail);
        if (!oldComponent.getAuthor().getEmail().equals(sanitizedEmail)) {
            throw new org.springframework.security.access.AccessDeniedException("Not authorized to modify this component");
        }

        oldComponent.setName(validationService.sanitizeInput(payload.getName()));
        oldComponent.setHtml(validationService.sanitizeHtml(payload.getHtml()));
        oldComponent.setCss(validationService.sanitizeCss(payload.getCss()));

        colorService.setComponentColor(oldComponent, payload);
        tagsService.setComponentTags(oldComponent, payload);

        return componentRepository.save(oldComponent);
    }
}