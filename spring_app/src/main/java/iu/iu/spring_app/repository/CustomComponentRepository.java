package iu.iu.spring_app.repository;

import iu.iu.spring_app.model.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomComponentRepository {
    public Integer addComponent(Component request, Set set, Color color, Type type);

    public Void addTags(Integer componentId, List<Tag> tags);
}
