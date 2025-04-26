package iu.iu.spring_app.api.components.service;

import iu.iu.spring_app.api.components.model.Color;
import iu.iu.spring_app.api.components.model.Component;
import iu.iu.spring_app.api.components.repository.ColorRepository;
import org.springframework.stereotype.Service;

@Service
public class ColorService {
    private final ColorRepository colorRepository;
    private final ValidationService validationService;

    public ColorService(ColorRepository colorRepository, ValidationService validationService) {
        this.colorRepository = colorRepository;
        this.validationService = validationService;
    }

    public void setComponentColor(Component component, Component payload) {
        if (payload.getColor() != null) {
            String sanitizedHex = validationService.sanitizeInput(payload.getColor().getHex().toUpperCase());
            if (!validationService.isValidHexColor(sanitizedHex)) {
                throw new IllegalArgumentException("Invalid color hex code");
            }

            Color color = colorRepository.findByHex(sanitizedHex);
            if (color == null) {
                color = new Color();
                color.setHex(sanitizedHex);
                color = colorRepository.save(color);
            }
            component.setColor(color);
        }
    }

}