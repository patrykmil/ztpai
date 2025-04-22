package iu.iu.spring_app.api.security.service;

import iu.iu.spring_app.api.errors.ResourceNotFoundException;
import iu.iu.spring_app.api.security.dto.JwtAuthenticationResponse;
import iu.iu.spring_app.api.security.dto.LoginRequest;
import iu.iu.spring_app.api.security.dto.RegisterRequest;
import iu.iu.spring_app.api.users.model.User;
import iu.iu.spring_app.api.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse register(RegisterRequest request) {
        var user = User
                .builder()
                .name(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .admin(false)
                .build();

        user = userService.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder()
                .token(jwt)
                .id(user.getId())
                .name(user.getName())
                .build();
    }

    public JwtAuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail()).
                orElseThrow(() -> new ResourceNotFoundException("User not found"));
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder()
                .token(jwt)
                .id(user.getId())
                .name(user.getName())
                .avatarPath(user.getAvatar().getAvatarPath())
                .build();
    }
}
