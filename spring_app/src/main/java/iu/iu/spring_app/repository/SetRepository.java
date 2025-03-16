package iu.iu.spring_app.repository;

import iu.iu.spring_app.model.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SetRepository extends JpaRepository<Set, Integer> {
    Set findByName(String name);
}
