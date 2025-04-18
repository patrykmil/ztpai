package iu.iu.spring_app;

import iu.iu.spring_app.api.security.controller.AuthenticationController;
import iu.iu.spring_app.api.security.dto.JwtAuthenticationResponse;
import iu.iu.spring_app.api.security.dto.SignInRequest;
import iu.iu.spring_app.api.security.dto.SignUpRequest;
import iu.iu.spring_app.api.security.service.AuthenticationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.BadCredentialsException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AuthenticationControllerTests {
    @Mock
    private AuthenticationService authenticationService;

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
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void register_Success() {
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .email("test@test.com")
                .password("password")
                .username("testuser")
                .build();

        when(authenticationService.signup(any(SignUpRequest.class)))
                .thenReturn(testJwtResponse);

        JwtAuthenticationResponse response = authenticationController.register(signUpRequest);

        assertEquals(testJwtResponse.getToken(), response.getToken());
        assertEquals(testJwtResponse.getId(), response.getId());
        assertEquals(testJwtResponse.getName(), response.getName());
    }

    @Test
    void login_Success() {
        SignInRequest signInRequest = SignInRequest.builder()
                .email("test@test.com")
                .password("password")
                .build();

        when(authenticationService.signin(any(SignInRequest.class)))
                .thenReturn(testJwtResponse);

        JwtAuthenticationResponse response = authenticationController.login(signInRequest);

        assertEquals(testJwtResponse.getToken(), response.getToken());
        assertEquals(testJwtResponse.getId(), response.getId());
        assertEquals(testJwtResponse.getName(), response.getName());
    }

    @Test
    void login_InvalidCredentials() {
        SignInRequest signInRequest = SignInRequest.builder()
                .email("test@test.com")
                .password("wrongpassword")
                .build();

        when(authenticationService.signin(any(SignInRequest.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        assertThrows(BadCredentialsException.class, () -> authenticationController.login(signInRequest));
    }
}