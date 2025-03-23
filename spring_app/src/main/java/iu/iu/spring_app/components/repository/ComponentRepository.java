package iu.iu.spring_app.components.repository;

import iu.iu.spring_app.components.model.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComponentRepository extends JpaRepository<Component, Integer>, CustomComponentRepository {
}
