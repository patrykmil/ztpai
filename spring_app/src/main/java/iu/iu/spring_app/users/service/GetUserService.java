package iu.iu.spring_app.users.service;

import iu.iu.spring_app.users.model.User;
import iu.iu.spring_app.users.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GetUserService {
    private final UserRepository userRepository;

    public GetUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByEmail(Map<String, String> payload) {
        return userRepository.findByEmail(payload.get("email"));
    }

    public User getUserByUsername(Map<String, String> payload) {
        return userRepository.findByUsername(payload.get("username"));
    }
}