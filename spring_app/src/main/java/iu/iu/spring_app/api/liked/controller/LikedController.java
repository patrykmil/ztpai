package iu.iu.spring_app.api.liked.controller;

import iu.iu.spring_app.api.liked.model.Liked;
import iu.iu.spring_app.api.liked.service.LikedService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@io.swagger.v3.oas.annotations.tags.Tag(name = "Component Likes", description = "APIs for managing component likes")
public class LikedController implements LikedControllerInterface {
    private final LikedService likedService;

    public LikedController(LikedService likedService) {
        this.likedService = likedService;
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Boolean> getIsLiked(Integer id, Authentication authentication) {
        Boolean liked = likedService.getIsLiked(id, authentication.getName());
        return ResponseEntity.ok(liked);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Liked> like(Integer id, Authentication authentication) {
        likedService.like(id, authentication.getName());
        return ResponseEntity.ok().build();
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Liked> unlike(Integer id, Authentication authentication) {
        likedService.unlike(id, authentication.getName());
        return ResponseEntity.ok().build();
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Liked> switchLike(Integer id, Authentication authentication) {
        likedService.switchLike(id, authentication.getName());
        return ResponseEntity.ok().build();
    }
}