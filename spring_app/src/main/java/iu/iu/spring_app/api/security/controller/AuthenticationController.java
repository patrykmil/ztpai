package iu.iu.spring_app.api.security.controller;

import iu.iu.spring_app.api.security.dto.JwtAuthenticationResponse;
import iu.iu.spring_app.api.security.dto.LoginRequest;
import iu.iu.spring_app.api.security.dto.RegisterRequest;
import iu.iu.spring_app.api.security.service.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Authentication", description = "APIs for user registration and authentication")
public class AuthenticationController implements AuthenticationControllerInterface {
    private final AuthenticationService authenticationService;

    @Value("${refresh.token.cookie.name}")
    private String refreshTokenCookieName;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    @PostMapping("/register")
    public JwtAuthenticationResponse register(RegisterRequest request, HttpServletResponse response) {
        return authenticationService.register(request, response);
    }

    @Override
    @PostMapping("/login")
        public JwtAuthenticationResponse login(LoginRequest request, HttpServletResponse response) {
        return authenticationService.login(request, response);
    }

    @Override
    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String refreshToken = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (refreshTokenCookieName.equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }

        if (refreshToken == null) {
            return ResponseEntity.badRequest().build();
        }

        JwtAuthenticationResponse response = authenticationService.refreshToken(refreshToken);
        return ResponseEntity.ok(response);
    }
}