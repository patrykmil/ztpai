package iu.iu.spring_app.api.messages.repository;

import iu.iu.spring_app.api.messages.model.Message;
import iu.iu.spring_app.api.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findByUser(User user);
}
