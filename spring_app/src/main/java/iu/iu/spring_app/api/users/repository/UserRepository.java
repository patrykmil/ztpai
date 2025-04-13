package iu.iu.spring_app.api.users.repository;

import iu.iu.spring_app.api.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    User findByName(String name);
}
