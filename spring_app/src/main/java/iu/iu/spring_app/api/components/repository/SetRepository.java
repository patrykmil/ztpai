package iu.iu.spring_app.api.components.repository;

import iu.iu.spring_app.api.components.model.Set;
import iu.iu.spring_app.api.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SetRepository extends JpaRepository<Set, Integer> {
    Optional<Set> findByName(String name);
    List<Set> findByUser(User user);
}
