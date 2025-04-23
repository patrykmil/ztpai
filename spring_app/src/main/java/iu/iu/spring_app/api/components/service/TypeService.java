package iu.iu.spring_app.api.components.service;

import iu.iu.spring_app.api.components.model.Type;
import iu.iu.spring_app.api.components.repository.TypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeService {
    private final TypeRepository typeRepository;

    public TypeService(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    public List<Type> getTypes() {
        return typeRepository.findAll();
    }

}
