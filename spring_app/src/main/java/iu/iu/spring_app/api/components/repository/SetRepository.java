package iu.iu.spring_app.api.components.repository;

import iu.iu.spring_app.api.components.model.Set;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SetRepository extends JpaRepository<Set, Integer> {
    Optional<Set> findByName(String name);
}
