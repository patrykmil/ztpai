package iu.iu.spring_app.api.liked.controller;

import iu.iu.spring_app.api.liked.model.Liked;
import iu.iu.spring_app.api.liked.service.LikedService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/components")
public class LikedController {
    private final LikedService likedService;
    public LikedController(LikedService likedService) {
        this.likedService = likedService;
    }

    @PostMapping("/like/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Liked> like(@PathVariable Integer id, Authentication authentication) {
        likedService.like(id, authentication.getName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/unlike/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Liked> unlike(@PathVariable Integer id, Authentication authentication) {
        likedService.unlike(id, authentication.getName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/switchLike/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Liked> switchLike(@PathVariable Integer id, Authentication authentication) {
        likedService.switchLike(id, authentication.getName());
        return ResponseEntity.ok().build();
    }
}
