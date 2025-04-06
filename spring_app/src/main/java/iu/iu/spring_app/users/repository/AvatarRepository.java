package iu.iu.spring_app.users.repository;

import iu.iu.spring_app.users.model.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Integer> {
    @Query(value = "SELECT * FROM avatar ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Avatar findRandom();
}