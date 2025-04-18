package iu.iu.spring_app.api.components.repository;

import iu.iu.spring_app.api.components.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypeRepository extends JpaRepository<Type, Integer> {
    Optional<Type> findByName(String name);
}
