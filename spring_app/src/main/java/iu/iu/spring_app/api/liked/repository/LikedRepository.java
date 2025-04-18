package iu.iu.spring_app.api.liked.repository;

import iu.iu.spring_app.api.liked.model.Liked;
import iu.iu.spring_app.api.liked.model.LikedId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikedRepository extends JpaRepository<Liked, LikedId> {
    Optional<Liked> findByUserIDAndComponentID(Integer userID, Integer componentID);
}
