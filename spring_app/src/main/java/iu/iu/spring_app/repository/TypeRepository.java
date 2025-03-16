package iu.iu.spring_app.repository;

import iu.iu.spring_app.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository extends JpaRepository<Type, Integer> {
    Type findByName(String name);
}
