package iu.iu.spring_app.api.security.service;

import iu.iu.spring_app.api.errors.InvalidTokenException;
import iu.iu.spring_app.api.errors.ResourceNotFoundException;
import iu.iu.spring_app.api.messages.model.Message;
import iu.iu.spring_app.api.messages.service.AddMessageService;
import iu.iu.spring_app.api.security.dto.JwtAuthenticationResponse;
import iu.iu.spring_app.api.security.dto.LoginRequest;
import iu.iu.spring_app.api.security.dto.RegisterRequest;
import iu.iu.spring_app.api.users.model.User;
import iu.iu.spring_app.api.users.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final AddMessageService addMessageService;

    @Value("${refresh.token.cookie.name}")
    private String refreshTokenCookieName;

    @Value("${refresh.token.cookie.max-age.s}")
    private int refreshTokenCookieMaxAge;

    public JwtAuthenticationResponse register(RegisterRequest request, HttpServletResponse response) {
        var user = User
                .builder()
                .name(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .admin(false)
                .build();

        user = userService.save(user);

        var message = Message.builder()
                .title("Welcome " + request.getUsername() + "!")
                .body("You can now share your work")
                .link("/create/")
                .user(user)
                .build();
        addMessageService.addMessage(message);

        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        addRefreshTokenCookie(response, refreshToken);

        return JwtAuthenticationResponse.builder()
                .token(jwt)
                .id(user.getId())
                .name(user.getName())
                .build();
    }

    public JwtAuthenticationResponse login(LoginRequest request, HttpServletResponse response) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail()).
                orElseThrow(() -> new ResourceNotFoundException("User not found"));
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        addRefreshTokenCookie(response, refreshToken);

        return JwtAuthenticationResponse.builder()
                .token(jwt)
                .id(user.getId())
                .name(user.getName())
                .avatarPath(user.getAvatar().getAvatarPath())
                .admin(user.getAdmin())
                .build();
    }

    public JwtAuthenticationResponse refreshToken(HttpServletRequest request) {
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
            throw new InvalidTokenException("Refresh token not found");
        }

        String userEmail = jwtService.extractUserName(refreshToken);

        if (userEmail != null) {
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);

            if (jwtService.isTokenValid(refreshToken, userDetails)) {
                var user = userRepository.findByEmail(userEmail)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found"));

                String newAccessToken = jwtService.generateToken(userDetails);

                return JwtAuthenticationResponse.builder()
                        .token(newAccessToken)
                        .id(user.getId())
                        .name(user.getName())
                        .avatarPath(user.getAvatar().getAvatarPath())
                        .admin(user.getAdmin())
                        .build();
            }
        }

        throw new InvalidTokenException("Invalid refresh token");
    }

    private void addRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        Cookie refreshTokenCookie = new Cookie(refreshTokenCookieName, refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setMaxAge(refreshTokenCookieMaxAge);
        refreshTokenCookie.setPath("/api/refresh");
        response.addCookie(refreshTokenCookie);
    }
}