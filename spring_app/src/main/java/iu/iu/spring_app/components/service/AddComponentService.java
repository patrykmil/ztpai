package iu.iu.spring_app.components.service;

import iu.iu.spring_app.components.model.*;
import iu.iu.spring_app.components.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class AddComponentService {
    private final ComponentRepository componentRepository;
    private final TagRepository tagRepository;
    private final SetRepository setRepository;
    private final ColorRepository colorRepository;
    private final TypeRepository typeRepository;

    public AddComponentService(ComponentRepository componentRepository, SetRepository setRepository, ColorRepository colorRepository, TagRepository tagRepository, TypeRepository typeRepository) {
        this.componentRepository = componentRepository;
        this.setRepository = setRepository;
        this.tagRepository = tagRepository;
        this.colorRepository = colorRepository;
        this.typeRepository = typeRepository;
    }

    @Transactional
    public ResponseEntity<Component> addComponent(Component request) {
        Integer id;
        try {
            Set set = setRepository.findByName(request.getSetName());
            if (set == null) {
                System.out.println(request.getSetName());
                return ResponseEntity.notFound().build();
            }

            Color color = colorRepository.findByHex(request.getHex());
            if (color == null) {
                System.out.println(request.getHex());
                return ResponseEntity.notFound().build();
            }

            Type type = typeRepository.findByName(request.getTypeName());
            if (type == null) {
                System.out.println(request.getTypeName());
                return ResponseEntity.notFound().build();
            }

            List<Tag> tags = new ArrayList<>();
            List<String> tagsString = List.of(request.getTags().split("\\s*,\\s*"));

            for (String tag : tagsString) {
                Tag t = tagRepository.findByName(tag);
                if (t == null) {
                    System.out.println(tag);
                    return ResponseEntity.notFound().build();
                }
                tags.add(t);
            }

            id = componentRepository.addComponent(request, set, color, type);
            componentRepository.addTagsToComponent(id, tags);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
        return componentRepository.findById(id)
                .map(component -> ResponseEntity.created(URI.create("/components/" + id)).body(component))
                .orElseGet(() -> ResponseEntity.internalServerError().build());
    }
}