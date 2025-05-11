package iu.iu.spring_app;

import iu.iu.spring_app.api.liked.controller.LikedController;
import iu.iu.spring_app.api.liked.service.LikedService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LikedControllerTests {

    @Mock
    private LikedService likedService;
    @Mock
    private Authentication authentication;

    @InjectMocks
    private LikedController likedController;

    @Test
    void getIsLiked_Success() {
        when(authentication.getName()).thenReturn("test@test.com");
        when(likedService.getIsLiked(1, "test@test.com")).thenReturn(true);

        ResponseEntity<Boolean> response = likedController.getIsLiked(1, authentication);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
    }

    @Test
    void like_Success() {
        when(authentication.getName()).thenReturn("test@test.com");
        doNothing().when(likedService).like(1, "test@test.com");

        ResponseEntity<?> response = likedController.like(1, authentication);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(likedService).like(1, "test@test.com");
    }

    @Test
    void unlike_Success() {
        when(authentication.getName()).thenReturn("test@test.com");
        doNothing().when(likedService).unlike(1, "test@test.com");

        ResponseEntity<?> response = likedController.unlike(1, authentication);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(likedService).unlike(1, "test@test.com");
    }

    @Test
    void switchLike_Success() {
        when(authentication.getName()).thenReturn("test@test.com");
        doNothing().when(likedService).switchLike(1, "test@test.com");

        ResponseEntity<?> response = likedController.switchLike(1, authentication);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(likedService).switchLike(1, "test@test.com");
    }
}