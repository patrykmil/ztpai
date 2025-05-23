package iu.iu.spring_app;

import iu.iu.spring_app.api.errors.InvalidTokenException;
import iu.iu.spring_app.api.security.controller.AuthenticationController;
import iu.iu.spring_app.api.security.dto.JwtAuthenticationResponse;
import iu.iu.spring_app.api.security.dto.LoginRequest;
import iu.iu.spring_app.api.security.dto.RegisterRequest;
import iu.iu.spring_app.api.security.service.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthenticationControllerTests {
    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private HttpServletResponse mockResponse;

    @Mock
    private HttpServletRequest mockRequest;

    private JwtAuthenticationResponse testJwtResponse;
    private AuthenticationController authenticationController;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        authenticationController = new AuthenticationController(authenticationService);

        testJwtResponse = JwtAuthenticationResponse.builder()
                .token("test.jwt.token")
                .id(1)
                .name("testuser")
                .admin(false)
                .avatarPath("default.jpg")
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void register_Success() {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .email("test@test.com")
                .password("password")
                .username("testuser")
                .build();

        when(authenticationService.register(any(RegisterRequest.class), any(HttpServletResponse.class)))
                .thenReturn(testJwtResponse);

        JwtAuthenticationResponse response = authenticationController.register(registerRequest, mockResponse);

        assertEquals(testJwtResponse.getToken(), response.getToken());
        assertEquals(testJwtResponse.getId(), response.getId());
        assertEquals(testJwtResponse.getName(), response.getName());
        verify(authenticationService).register(registerRequest, mockResponse);
    }

    @Test
    void register_ValidationFailure() {
        RegisterRequest invalidRequest = RegisterRequest.builder()
                .email("")
                .password("")
                .username("")
                .build();

        when(authenticationService.register(any(RegisterRequest.class), any(HttpServletResponse.class)))
                .thenThrow(new IllegalArgumentException("Invalid registration data"));

        assertThrows(IllegalArgumentException.class,
                () -> authenticationController.register(invalidRequest, mockResponse));
    }

    @Test
    void login_Success() {
        LoginRequest loginRequest = LoginRequest.builder()
                .email("test@test.com")
                .password("password")
                .build();

        when(authenticationService.login(any(LoginRequest.class), any(HttpServletResponse.class)))
                .thenReturn(testJwtResponse);

        JwtAuthenticationResponse response = authenticationController.login(loginRequest, mockResponse);

        assertEquals(testJwtResponse.getToken(), response.getToken());
        assertEquals(testJwtResponse.getId(), response.getId());
        assertEquals(testJwtResponse.getName(), response.getName());
        verify(authenticationService).login(loginRequest, mockResponse);
    }

    @Test
    void login_InvalidCredentials() {
        LoginRequest loginRequest = LoginRequest.builder()
                .email("test@test.com")
                .password("wrongpassword")
                .build();

        when(authenticationService.login(any(LoginRequest.class), any(HttpServletResponse.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        assertThrows(BadCredentialsException.class,
                () -> authenticationController.login(loginRequest, mockResponse));
    }

    @Test
    void refreshToken_Success() {
        Cookie[] cookies = new Cookie[]{
                new Cookie("refreshToken", "valid.refresh.token")
        };
        when(mockRequest.getCookies()).thenReturn(cookies);
        when(authenticationService.refreshToken(mockRequest))
                .thenReturn(testJwtResponse);

        ResponseEntity<JwtAuthenticationResponse> response =
                authenticationController.refreshToken(mockRequest);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertEquals(testJwtResponse.getToken(), response.getBody().getToken());
        verify(authenticationService).refreshToken(mockRequest);
    }

    @Test
    void refreshToken_NoCookie() {
        when(mockRequest.getCookies()).thenReturn(null);
        when(authenticationService.refreshToken(mockRequest))
                .thenThrow(new InvalidTokenException("Refresh token not found"));

        assertThrows(InvalidTokenException.class,
                () -> authenticationController.refreshToken(mockRequest));
    }

    @Test
    void refreshToken_InvalidToken() {
        Cookie[] cookies = new Cookie[]{
                new Cookie("refreshToken", "invalid.refresh.token")
        };
        when(mockRequest.getCookies()).thenReturn(cookies);
        when(authenticationService.refreshToken(mockRequest))
                .thenThrow(new InvalidTokenException("Invalid refresh token"));

        assertThrows(InvalidTokenException.class,
                () -> authenticationController.refreshToken(mockRequest));
    }

    @Test
    void refreshToken_WrongCookieName() {
        Cookie[] cookies = new Cookie[]{
                new Cookie("wrongCookieName", "valid.refresh.token")
        };
        when(mockRequest.getCookies()).thenReturn(cookies);
        when(authenticationService.refreshToken(mockRequest))
                .thenThrow(new InvalidTokenException("Refresh token not found"));

        assertThrows(InvalidTokenException.class,
                () -> authenticationController.refreshToken(mockRequest));
    }

    @Test
    void refreshToken_ExpiredToken() {
        Cookie[] cookies = new Cookie[]{
                new Cookie("refreshToken", "expired.refresh.token")
        };
        when(mockRequest.getCookies()).thenReturn(cookies);
        when(authenticationService.refreshToken(mockRequest))
                .thenThrow(new InvalidTokenException("Token expired"));

        assertThrows(InvalidTokenException.class,
                () -> authenticationController.refreshToken(mockRequest));
    }
}