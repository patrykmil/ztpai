package iu.iu.spring_app.api.components.service;

import iu.iu.spring_app.api.components.model.Component;
import iu.iu.spring_app.api.components.repository.ComponentRepository;
import iu.iu.spring_app.api.messages.model.Message;
import iu.iu.spring_app.api.messages.service.AddMessageService;
import org.springframework.stereotype.Service;


@Service
public class DeleteComponentService {
    private final ComponentRepository componentRepository;
    private final GetComponentService getComponentService;
    private final AddMessageService addMessageService;

    public DeleteComponentService(ComponentRepository componentRepository, GetComponentService getComponentService, AddMessageService addMessageService) {
        this.componentRepository = componentRepository;
        this.getComponentService = getComponentService;
        this.addMessageService = addMessageService;
    }

    public void deleteComponentById(Integer componentId, String userEmail) {
        Component component = getComponentService.getComponentById(componentId);

        if (!component.getAuthor().getEmail().equals(userEmail)) {
            throw new org.springframework.security.access.AccessDeniedException("Not authorized to delete this component");
        }

        componentRepository.delete(component);

        var message = Message.builder()
                .title("Component \"" + component.getName() + "\" has been deleted")
                .user(component.getAuthor())
                .build();
        addMessageService.addMessage(message);
    }
}