package iu.iu.spring_app.api.components.service;

import iu.iu.spring_app.api.components.model.Component;
import iu.iu.spring_app.api.components.model.Set;
import iu.iu.spring_app.api.components.model.Type;
import iu.iu.spring_app.api.components.repository.ComponentRepository;
import iu.iu.spring_app.api.components.repository.SetRepository;
import iu.iu.spring_app.api.components.repository.TypeRepository;
import iu.iu.spring_app.api.errors.ResourceNotFoundException;
import iu.iu.spring_app.api.users.model.User;
import iu.iu.spring_app.api.users.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddComponentService {
    private final ComponentRepository componentRepository;
    private final SetRepository setRepository;
    private final TypeRepository typeRepository;
    private final UserRepository userRepository;
    private final TagsService tagsService;
    private final ColorService colorService;

    public AddComponentService(ComponentRepository componentRepository,
                               SetRepository setRepository,
                               TypeRepository typeRepository,
                               UserRepository userRepository,
                               TagsService tagsService,
                               ColorService colorService) {
        this.componentRepository = componentRepository;
        this.setRepository = setRepository;
        this.typeRepository = typeRepository;
        this.userRepository = userRepository;
        this.tagsService = tagsService;
        this.colorService = colorService;
    }

    @Transactional
    public Component addComponent(Component request, String userEmail) {
        System.out.println(request);
        User author = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (!author.getName().equals(request.getAuthor().getName())) {
            throw new org.springframework.security.access.AccessDeniedException("Not authorized to create component for another user");
        }

        Type type = typeRepository.findByName(request.getType().getName())
                .orElseThrow(() -> new ResourceNotFoundException("Type not found"));

        Set set = setRepository.findByName(request.getSet().getName())
                .orElseThrow(() -> new ResourceNotFoundException("Set not found"));

        Component component = Component.builder()
                        .name(request.getName())
                        .html(request.getHtml())
                        .css(request.getCss())
                        .author(author)
                        .type(type)
                        .build();
        component.setSet(set);



        colorService.setComponentColor(component, request);
        tagsService.setComponentTags(component, request);

        return componentRepository.save(component);
    }
}