package iu.iu.spring_app.api.liked.service;

import iu.iu.spring_app.api.components.repository.ComponentRepository;
import iu.iu.spring_app.api.errors.ResourceNotFoundException;
import iu.iu.spring_app.api.liked.model.Liked;
import iu.iu.spring_app.api.liked.repository.LikedRepository;
import iu.iu.spring_app.api.users.model.User;
import iu.iu.spring_app.api.users.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikedService {
    private final LikedRepository likedRepository;
    private final UserRepository userRepository;
    private final ComponentRepository componentRepository;

    public LikedService(LikedRepository likedRepository, UserRepository userRepository, ComponentRepository componentRepository) {
        this.likedRepository = likedRepository;
        this.userRepository = userRepository;
        this.componentRepository = componentRepository;
    }

    @Transactional
    public void like(Integer componentId, String email) {
        User user = getUserByEmail(email);
        validateComponent(componentId);

        if (likedRepository.findByUserIDAndComponentID(user.getId(), componentId).isPresent()) {
            throw new IllegalStateException("Component already liked");
        }

        Liked liked = createLiked(user.getId(), componentId);
        likedRepository.save(liked);
    }

    @Transactional
    public void unlike(Integer componentId, String email) {
        User user = getUserByEmail(email);
        likedRepository.findByUserIDAndComponentID(user.getId(), componentId)
                .ifPresentOrElse(
                        likedRepository::delete,
                        () -> { throw new ResourceNotFoundException("Component wasn't liked before"); }
                );
    }

    @Transactional
    public void switchLike(Integer componentId, String email) {
        User user = getUserByEmail(email);
        validateComponent(componentId);

        likedRepository.findByUserIDAndComponentID(user.getId(), componentId)
                .ifPresentOrElse(
                        likedRepository::delete,
                        () -> likedRepository.save(createLiked(user.getId(), componentId))
                );
    }

    private User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        return user;
    }

    private void validateComponent(Integer componentId) {
        if (!componentRepository.existsById(componentId)) {
            throw new ResourceNotFoundException("Component not found");
        }
    }

    private Liked createLiked(Integer userId, Integer componentId) {
        return Liked.builder()
                .userID(userId)
                .componentID(componentId)
                .build();
    }
}