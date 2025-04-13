package iu.iu.spring_app.api.components.repository;

import iu.iu.spring_app.api.components.model.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComponentRepository extends JpaRepository<Component, Integer> {
}
