package iu.iu.spring_app.api.components.service;

import iu.iu.spring_app.api.components.model.Component;
import iu.iu.spring_app.api.components.repository.ComponentRepository;
import iu.iu.spring_app.api.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetComponentService {
    private final ComponentRepository componentRepository;
    private final ValidationService validationService;

    public GetComponentService(ComponentRepository componentRepository, ValidationService validationService) {
        this.componentRepository = componentRepository;
        this.validationService = validationService;
    }

    public List<Component> getAllComponents() {
        return componentRepository.findAll()
                .stream()
                .peek(validationService::unescapeComponent)
                .collect(Collectors.toList());
    }

    public Component getComponentById(Integer id) {
        Component component = componentRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Component not found"));

        validationService.unescapeComponent(component);

        return component;
    }
}
