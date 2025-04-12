package iu.iu.spring_app.components.service;

import iu.iu.spring_app.components.model.Component;
import iu.iu.spring_app.components.repository.ComponentRepository;
import iu.iu.spring_app.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ReplaceComponentService {
    private final ComponentRepository componentRepository;
    private final GetComponentService getComponentService;
    private final TagsService tagsService;
    private final ColorService colorService;

    public ReplaceComponentService(ComponentRepository componentRepository,
                                   GetComponentService getComponentService,
                                   TagsService tagsService,
                                   ColorService colorService) {
        this.componentRepository = componentRepository;
        this.getComponentService = getComponentService;
        this.tagsService = tagsService;
        this.colorService = colorService;
    }
    public Component replaceComponent(Component request) {
        Component oldComponent = getComponentService.getComponentById(request.getId());
        if (oldComponent == null) {
            throw new ResourceNotFoundException("Component " + request.getId() + " not found");
        }
        oldComponent.setName(request.getName());
        oldComponent.setHtml(request.getHtml());
        oldComponent.setCss(request.getCss());

        colorService.setComponentColor(oldComponent, request);
        tagsService.setComponentTags(oldComponent, request);

        return componentRepository.save(oldComponent);
    }

}
