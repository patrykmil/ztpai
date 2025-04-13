package iu.iu.spring_app.api.components.repository;

import iu.iu.spring_app.api.components.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<Color, Integer> {
    Color findByHex(String hex);
}
