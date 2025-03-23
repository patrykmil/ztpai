package iu.iu.spring_app.components.repository;

import iu.iu.spring_app.components.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    Tag findByName(String name);
}
