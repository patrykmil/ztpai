package iu.iu.spring_app.components.service;

import iu.iu.spring_app.components.model.Component;
import iu.iu.spring_app.components.model.Set;
import iu.iu.spring_app.components.model.Type;
import iu.iu.spring_app.components.repository.ComponentRepository;
import iu.iu.spring_app.components.repository.SetRepository;
import iu.iu.spring_app.components.repository.TypeRepository;
import iu.iu.spring_app.errors.ResourceNotFoundException;
import iu.iu.spring_app.users.model.User;
import iu.iu.spring_app.users.repository.UserRepository;
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
    public Component addComponent(Component request) {
        Component component = new Component();
        component.setName(request.getName());
        component.setHtml(request.getHtml());
        component.setCss(request.getCss());

        User author = userRepository.findByUsername(request.getAuthor().getUsername());
        component.setAuthor(author);

        Type type = typeRepository.findByName(request.getType().getName());
        if (type == null) {
            throw (new ResourceNotFoundException("Type " + request.getType().getName() + " not found"));
        }
        component.setType(type);


        if (request.getSet() != null && request.getSet().getName() != null) {
            Set set = setRepository.findByName(request.getSet().getName());
            component.setSet(set);
        }

        colorService.setComponentColor(component, request);
        tagsService.setComponentTags(component, request);

        return componentRepository.save(component);
    }
}