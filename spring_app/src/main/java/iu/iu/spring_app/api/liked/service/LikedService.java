package iu.iu.spring_app.api.liked.service;

import iu.iu.spring_app.api.components.model.Component;
import iu.iu.spring_app.api.components.repository.ComponentRepository;
import iu.iu.spring_app.api.components.service.GetComponentService;
import iu.iu.spring_app.api.errors.ResourceNotFoundException;
import iu.iu.spring_app.api.liked.model.Liked;
import iu.iu.spring_app.api.liked.repository.LikedRepository;
import iu.iu.spring_app.api.users.model.User;
import iu.iu.spring_app.api.users.repository.UserRepository;
import iu.iu.spring_app.api.users.service.GetUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikedService {
    private final LikedRepository likedRepository;
    private final UserRepository userRepository;
    private final ComponentRepository componentRepository;
    private final GetUserService getUserService;
    private final GetComponentService getComponentService;

    public LikedService(LikedRepository likedRepository,
                        UserRepository userRepository,
                        ComponentRepository componentRepository,
                        GetUserService getUserService,
                        GetComponentService getComponentService) {
        this.likedRepository = likedRepository;
        this.userRepository = userRepository;
        this.componentRepository = componentRepository;
        this.getUserService = getUserService;
        this.getComponentService = getComponentService;
    }

    @Transactional
    public void like(Integer componentId, String email) {
        User user = getUserService.getUserByEmail(email);
        Component component = getComponentService.getComponentById(componentId);

        if (likedRepository.findByUserIDAndComponentID(user.getId(), componentId).isPresent()) {
            throw new IllegalStateException("Component already liked");
        }

        Liked liked = createLiked(user.getId(), componentId);
        likedRepository.save(liked);
    }

    @Transactional
    public void unlike(Integer componentId, String email) {
        User user = getUserService.getUserByEmail(email);
        likedRepository.findByUserIDAndComponentID(user.getId(), componentId)
                .ifPresentOrElse(
                        likedRepository::delete,
                        () -> { throw new ResourceNotFoundException("Component not liked"); }
                );
    }

    @Transactional
    public void switchLike(Integer componentId, String email) {
        User user = getUserService.getUserByEmail(email);
        Component component = getComponentService.getComponentById(componentId);

        likedRepository.findByUserIDAndComponentID(user.getId(), componentId)
                .ifPresentOrElse(
                        likedRepository::delete,
                        () -> likedRepository.save(createLiked(user.getId(), componentId))
                );
    }

    @Transactional
    public Boolean getIsLiked(Integer componentId, String email) {
        User user = getUserService.getUserByEmail(email);
        return likedRepository.findByUserIDAndComponentID(user.getId(), componentId).isPresent();
    }

    private Liked createLiked(Integer userId, Integer componentId) {
        return Liked.builder()
                .userID(userId)
                .componentID(componentId)
                .build();
    }
}