package iu.iu.spring_app.api.security.controller;

import iu.iu.spring_app.api.security.dto.JwtAuthenticationResponse;
import iu.iu.spring_app.api.security.dto.LoginRequest;
import iu.iu.spring_app.api.security.dto.RegisterRequest;
import iu.iu.spring_app.api.security.service.AuthenticationService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@io.swagger.v3.oas.annotations.tags.Tag(name = "Authentication", description = "APIs for user registration and authentication")
public class AuthenticationController implements AuthenticationControllerInterface {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public JwtAuthenticationResponse register(RegisterRequest request) {
        return authenticationService.register(request);
    }

    @Override
    public JwtAuthenticationResponse login(LoginRequest request) {
        return authenticationService.login(request);
    }
}