package iu.iu.spring_app.components.service;

import iu.iu.spring_app.components.model.Component;
import iu.iu.spring_app.components.repository.ComponentRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DeleteComponentService {
    private final ComponentRepository componentRepository;
    private final GetComponentService getComponentService;

    public DeleteComponentService(ComponentRepository componentRepository, GetComponentService getComponentService) {
        this.componentRepository = componentRepository;
        this.getComponentService = getComponentService;
    }

    public void deleteComponentById(Map<String, Integer> request) {
        Component component = getComponentService.getComponentById(request.get("componentId"));
        if (component == null) {
            throw new RuntimeException("Component not found");
        }
        componentRepository.delete(component);
    }
}
