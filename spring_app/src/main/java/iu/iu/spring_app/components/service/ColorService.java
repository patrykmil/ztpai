package iu.iu.spring_app.components.service;

import iu.iu.spring_app.components.model.Color;
import iu.iu.spring_app.components.model.Component;
import iu.iu.spring_app.components.repository.ColorRepository;
import org.springframework.stereotype.Service;

@Service
public class ColorService {
    private final ColorRepository colorRepository;

    public ColorService(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    public void setComponentColor(Component component, Component request) {
        if (request.getColor() != null) {
            Color color = colorRepository.findByHex(request.getColor().getHex().toUpperCase());
            if (color == null) {
                color = new Color();
                color.setHex(component.getColor().getHex());
                color = colorRepository.save(color);
            }
            component.setColor(color);
        }
    }
}
