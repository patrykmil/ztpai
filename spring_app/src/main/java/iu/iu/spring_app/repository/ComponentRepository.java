package iu.iu.spring_app.repository;

import iu.iu.spring_app.model.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComponentRepository extends JpaRepository<Component, Integer> {
}
