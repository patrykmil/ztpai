package iu.iu.spring_app.components.repository;

import iu.iu.spring_app.components.model.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomComponentRepository {
    public Integer addComponent(Component request, Set set, Color color, Type type);

    public void addTagsToComponent(Integer componentId, List<Tag> tags);
}
