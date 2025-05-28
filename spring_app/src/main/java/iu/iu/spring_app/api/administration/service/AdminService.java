package iu.iu.spring_app.api.administration.service;

import iu.iu.spring_app.api.administration.model.DeleteComponentModel;
import iu.iu.spring_app.api.components.model.Component;
import iu.iu.spring_app.api.components.repository.ComponentRepository;
import iu.iu.spring_app.api.errors.ResourceNotFoundException;
import iu.iu.spring_app.api.messages.model.Message;
import iu.iu.spring_app.api.messages.service.AddMessageService;
import iu.iu.spring_app.api.users.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final UserRepository userRepository;
    private final ComponentRepository componentRepository;
    private final AddMessageService addMessageService;

    public AdminService(UserRepository userRepository,
                        ComponentRepository componentRepository,
                        AddMessageService addMessageService) {
        this.userRepository = userRepository;
        this.componentRepository = componentRepository;
        this.addMessageService = addMessageService;
    }

    public void removeComponent(DeleteComponentModel deleteComponentModel) {
        Component component = componentRepository.findById(deleteComponentModel.getComponentId())
                .orElseThrow(() -> new ResourceNotFoundException("Component not found"));
        componentRepository.delete(component);
        var message = Message.builder()
                .title("Component \"" + component.getName() + "\" has been deleted by admin")
                .body(deleteComponentModel.getReason())
                .user(component.getAuthor())
                .build();
        addMessageService.addMessage(message);
    }

    public void banUser(Integer userId) {
        userRepository.deleteById(userId);
    }
}
