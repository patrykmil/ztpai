package iu.iu.spring_app.api.components.repository;

import iu.iu.spring_app.api.components.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository extends JpaRepository<Type, Integer> {
    Type findByName(String name);
}
