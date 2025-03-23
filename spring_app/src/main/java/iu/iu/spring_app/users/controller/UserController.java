package iu.iu.spring_app.users.controller;

import iu.iu.spring_app.users.repository.UserRepository;
import iu.iu.spring_app.users.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/users")
    public ResponseEntity<User> getUserByEmail(@RequestBody Map<String, String> payload) {
        User user = userRepository.findByEmail(payload.get("email"));
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PostMapping("/addUser")
    public ResponseEntity<User> addUser(@RequestBody Map<String, String> payload) {
        User user = userRepository.findByEmail(payload.get("email"));
        if (user != null) {
            return ResponseEntity.status(409).body(null);
        }
        user = new User();
        user.setEmail(payload.get("email"));
        user.setUsername(payload.get("username"));
        user.setPassword(passwordEncoder.encode(payload.get("password")));
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }
}