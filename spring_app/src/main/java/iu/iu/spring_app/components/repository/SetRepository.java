package iu.iu.spring_app.components.repository;

import iu.iu.spring_app.components.model.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SetRepository extends JpaRepository<Set, Integer> {
    Set findByName(String name);
}
