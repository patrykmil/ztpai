package iu.iu.spring_app.api.components.repository;

import iu.iu.spring_app.api.components.model.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SetRepository extends JpaRepository<Set, Integer> {
    Set findByName(String name);
}
