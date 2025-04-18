package iu.iu.spring_app.api.components.service;

import iu.iu.spring_app.api.components.model.Component;
import iu.iu.spring_app.api.components.repository.ComponentRepository;
import iu.iu.spring_app.api.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class DeleteComponentService {
    private final ComponentRepository componentRepository;
    private final GetComponentService getComponentService;

    public DeleteComponentService(ComponentRepository componentRepository, GetComponentService getComponentService) {
        this.componentRepository = componentRepository;
        this.getComponentService = getComponentService;
    }

    public void deleteComponentById(Integer componentId, String userEmail) {
        Component component = getComponentService.getComponentById(componentId);
        if (component == null) {
            throw new ResourceNotFoundException("Component " + componentId + " not found");
        }
        if (!component.getAuthor().getEmail().equals(userEmail)) {
            throw new org.springframework.security.access.AccessDeniedException("Not authorized to delete this component");
        }
        componentRepository.delete(component);
    }
}