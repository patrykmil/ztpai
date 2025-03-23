package iu.iu.spring_app.components.repository;

import iu.iu.spring_app.components.model.*;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ComponentRepositoryImpl implements CustomComponentRepository {
    private final EntityManager entityManager;

    public ComponentRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
//    @Transactional
    public Integer addComponent(Component request, Set set, Color color, Type type) {
        String query =
                "INSERT INTO public.component(component_name, set_id, type_id, color_id, user_id, html, css)" +
                        "VALUES (:name, :setId, :typeId, :colorId, :userId, :html, :css) RETURNING component_id";

        return (Integer) entityManager.createNativeQuery(query)
                .setParameter("name", request.getName())
                .setParameter("setId", set.getId())
                .setParameter("typeId", type.getId())
                .setParameter("colorId", color.getId())
                .setParameter("userId", request.getUserId())
                .setParameter("html", request.getHtml())
                .setParameter("css", request.getCss())
                .getSingleResult();
    }

    @Override
//    @Transactional
    public void addTagsToComponent(Integer componentId, List<Tag> tags) {
        for (Tag tag : tags) {
            String query = "INSERT INTO public.component_tag (component_id, tag_id) VALUES (:component, :tag)";
            entityManager.createNativeQuery(query)
                    .setParameter("component", componentId)
                    .setParameter("tag", tag.getId())
                    .executeUpdate();

        }
    }


}
