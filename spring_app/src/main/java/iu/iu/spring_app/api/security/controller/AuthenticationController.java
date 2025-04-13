package iu.iu.spring_app.api.security.controller;

import iu.iu.spring_app.api.security.dto.JwtAuthenticationResponse;
import iu.iu.spring_app.api.security.dto.SignInRequest;
import iu.iu.spring_app.api.security.dto.SignUpRequest;
import iu.iu.spring_app.api.security.service.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public JwtAuthenticationResponse register(@RequestBody SignUpRequest request) {
        return authenticationService.signup(request);
    }

    @PostMapping("/login")
    public JwtAuthenticationResponse login(@RequestBody SignInRequest request) {
        return authenticationService.signin(request);
    }
}
