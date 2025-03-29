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
            component.setHtml(StringEscapeUtils.unescapeHtml4(component.getHtml()));
            component.setCss(StringEscapeUtils.unescapeHtml4(component.getCss()));
        }
        return components;
    }

    public Component getComponentById(Integer id) {
        Component component = componentRepository.findById(id).orElse(null);
        if (component != null) {
            component.setHtml(StringEscapeUtils.unescapeHtml4(component.getHtml()));
            component.setCss(StringEscapeUtils.unescapeHtml4(component.getCss()));
        }
        return component;
    }
}