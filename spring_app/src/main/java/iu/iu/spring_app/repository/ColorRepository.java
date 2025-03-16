package iu.iu.spring_app.repository;

import iu.iu.spring_app.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<Color, Integer> {
    Color findByHex(String hex);
}
