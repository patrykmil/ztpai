package iu.iu.spring_app.users.service;

import iu.iu.spring_app.temp_default.model.ErrorMessage;
import iu.iu.spring_app.users.model.User;
import iu.iu.spring_app.users.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Map;

@Service
public class AddUserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AddUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public ResponseEntity<?> addUser(Map<String, String> payload) {
        User user = userRepository.findByEmail(payload.get("email"));
        if (user != null) {
            return ResponseEntity.status(409).body(new ErrorMessage("User with that email address already exists"));
        }
        user = new User();
        user.setEmail(payload.get("email"));
        user.setUsername(payload.get("username"));
        user.setPassword(passwordEncoder.encode(payload.get("password")));
        userRepository.save(user);
        Integer id = userRepository.findByEmail(payload.get("email")).getId();
        return id > 0 ?
                ResponseEntity.created(URI.create("/users/" + id)).body(user) :
                ResponseEntity.internalServerError().build();
    }
}