package iu.iu.spring_app.api.liked.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import iu.iu.spring_app.api.errors.ExceptionResponse;
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
@io.swagger.v3.oas.annotations.tags.Tag(name = "Component Likes", description = "APIs for managing component likes")
public class LikedController {
    private final LikedService likedService;
    public LikedController(LikedService likedService) {
        this.likedService = likedService;
    }

    @Operation(summary = "Like a component", description = "Adds a like to a component for the authenticated user")
    @ApiResponse(responseCode = "200", description = "Component liked successfully")
    @ApiResponse(responseCode = "403", description = "User is not permitted", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "Component not found", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @PostMapping("/like/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Liked> like(@PathVariable Integer id, Authentication authentication) {
        likedService.like(id, authentication.getName());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Unlike a component", description = "Removes a like from a component for the authenticated user")
    @ApiResponse(responseCode = "200", description = "Component unliked successfully")
    @ApiResponse(responseCode = "403", description = "User is not permitted", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "Component not found", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @PostMapping("/unlike/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Liked> unlike(@PathVariable Integer id, Authentication authentication) {
        likedService.unlike(id, authentication.getName());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Toggle like status", description = "Toggles like status for a component for the authenticated user")
    @ApiResponse(responseCode = "200", description = "Like status toggled successfully")
    @ApiResponse(responseCode = "403", description = "User is not permitted", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @ApiResponse(responseCode = "404", description = "Component not found", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @PostMapping("/switchLike/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Liked> switchLike(@PathVariable Integer id, Authentication authentication) {
        likedService.switchLike(id, authentication.getName());
        return ResponseEntity.ok().build();
    }
}
