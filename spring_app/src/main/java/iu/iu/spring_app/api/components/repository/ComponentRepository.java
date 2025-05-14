package iu.iu.spring_app.api.components.repository;

import iu.iu.spring_app.api.components.model.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComponentRepository extends JpaRepository<Component, Integer> {
    @Query("SELECT c FROM Component c JOIN Liked l ON c.id = l.componentID WHERE l.userID = :userId")
    List<Component> findAllLikedByUserId(@Param("userId") Integer userId);

    @Query("SELECT c FROM Component c WHERE c.set.id = :setId")
    List<Component> findAllBySetId(@Param("setId") Integer setId);
}
