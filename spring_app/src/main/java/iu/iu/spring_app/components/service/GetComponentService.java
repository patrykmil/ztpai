package iu.iu.spring_app.components.service;

import iu.iu.spring_app.components.model.Component;
import iu.iu.spring_app.components.repository.ComponentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetComponentService {
    private final ComponentRepository componentRepository;

    public GetComponentService(ComponentRepository componentRepository) {
        this.componentRepository = componentRepository;
    }

    public List<Component> getAllComponents() {
        return componentRepository.findAll();
    }

    public Component getComponentById(Integer id) {
        return componentRepository.findById(id).orElse(null);
    }
}