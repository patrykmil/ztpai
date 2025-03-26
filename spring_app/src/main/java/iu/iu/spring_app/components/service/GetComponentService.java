package iu.iu.spring_app.components.service;

import iu.iu.spring_app.components.model.Component;
import iu.iu.spring_app.components.repository.ComponentRepository;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetComponentService {
    private final ComponentRepository componentRepository;

    public GetComponentService(ComponentRepository componentRepository) {
        this.componentRepository = componentRepository;
    }

    public List<Component> getAllComponents() {
        List<Component> components = componentRepository.findAll();
        for (Component component : components) {
            String unescaped = StringEscapeUtils.unescapeHtml4(component.getHtml());
            component.setHtml(unescaped);
            System.out.println(unescaped);
        }
        return components;
    }

    public Component getComponentById(Integer id) {
        return componentRepository.findById(id).orElse(null);
    }
}