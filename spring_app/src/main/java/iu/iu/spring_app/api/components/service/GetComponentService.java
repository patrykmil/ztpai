package iu.iu.spring_app.api.components.service;

import iu.iu.spring_app.api.components.model.Component;
import iu.iu.spring_app.api.components.repository.ComponentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetComponentService {
    private final ComponentRepository componentRepository;
    private final ValidationService validationService;

    public GetComponentService(ComponentRepository componentRepository, ValidationService validationService) {
        this.componentRepository = componentRepository;
        this.validationService = validationService;
    }

    public List<Component> getAllComponents() {
        List<Component> components = componentRepository.findAll();
        components.forEach(validationService::unescapeComponent);
        return components;
    }

    public Component getComponentById(Integer id) {
        Component component = componentRepository.findById(id).orElse(null);
        if (component != null) {
            validationService.unescapeComponent(component);
        }
        return component;
    }
}
