package iu.iu.spring_app.api.security.controller;

import iu.iu.spring_app.api.security.dto.JwtAuthenticationResponse;
import iu.iu.spring_app.api.security.dto.LoginRequest;
import iu.iu.spring_app.api.security.dto.RegisterRequest;
import iu.iu.spring_app.api.security.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Authentication", description = "APIs for user registration and authentication")
public class AuthenticationController implements AuthenticationControllerInterface {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public JwtAuthenticationResponse register(RegisterRequest request, HttpServletResponse response) {
        return authenticationService.register(request, response);
    }

    @Override
        public JwtAuthenticationResponse login(LoginRequest request, HttpServletResponse response) {
        return authenticationService.login(request, response);
    }

    @Override
    public ResponseEntity<JwtAuthenticationResponse> refreshToken(HttpServletRequest request) {
        JwtAuthenticationResponse response = authenticationService.refreshToken(request);
        return ResponseEntity.ok(response);
    }
}