package iu.iu.spring_app.users.service;

import iu.iu.spring_app.errors.ErrorMessage;
import iu.iu.spring_app.users.model.User;
import iu.iu.spring_app.users.repository.AvatarRepository;
import iu.iu.spring_app.users.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Map;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AvatarRepository avatarRepository;

    public AuthenticationService(UserRepository userRepository, AvatarRepository avatarRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.avatarRepository = avatarRepository;
    }

    public ResponseEntity<?> register(Map<String, String> payload) {
        User user = userRepository.findByEmail(payload.get("email"));
        if (user != null) {
            return ResponseEntity.status(409).body(new ErrorMessage("User with that email address already exists"));
        }
        user = userRepository.findByUsername(payload.get("username"));
        if (user != null) {
            return ResponseEntity.status(409).body(new ErrorMessage("User with that username already exists"));
        }
        user = new User();
        user.setEmail(payload.get("email"));
        user.setUsername(payload.get("username"));
        user.setPassword(passwordEncoder.encode(payload.get("password")));
        user.setAvatar(avatarRepository.findRandom());
        userRepository.save(user);
        Integer id = userRepository.findByEmail(payload.get("email")).getId();
        return id > 0 ?
                ResponseEntity.created(URI.create("/users/" + id)).body(user) :
                ResponseEntity.internalServerError().build();
    }

    public ResponseEntity<?> login(Map<String, String> payload) {
        User user = userRepository.findByEmail(payload.get("email"));
        if (user == null) {
            return ResponseEntity.status(401).body(new ErrorMessage("Invalid email or password"));
        }
        if (!passwordEncoder.matches(payload.get("password"), user.getPassword())) {
            return ResponseEntity.status(401).body(new ErrorMessage("Invalid password"));
        }
        return ResponseEntity.ok(user);
    }
}