package iu.iu.spring_app.api.security.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import iu.iu.spring_app.api.errors.ExceptionResponse;
import iu.iu.spring_app.api.security.dto.JwtAuthenticationResponse;
import iu.iu.spring_app.api.security.dto.LoginRequest;
import iu.iu.spring_app.api.security.dto.RegisterRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("")
public interface AuthenticationControllerInterface {
    @Operation(summary = "Register new user", description = "Creates a new user account and returns JWT token")
    @ApiResponse(responseCode = "200", description = "User registered successfully",
            content = @Content(schema = @Schema(implementation = JwtAuthenticationResponse.class)))
    @PostMapping("/register")
    JwtAuthenticationResponse register(@RequestBody RegisterRequest request);

    @Operation(summary = "Login user", description = "Authenticates user and returns JWT token")
    @ApiResponse(responseCode = "200", description = "Login successful",
            content = @Content(schema = @Schema(implementation = JwtAuthenticationResponse.class)))
    @ApiResponse(responseCode = "404", description = "User not found",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    @PostMapping("/login")
    JwtAuthenticationResponse login(@RequestBody LoginRequest request);
}