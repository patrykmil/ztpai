package iu.iu.spring_app.users.service;

import iu.iu.spring_app.errors.ResourceNotFoundException;
import iu.iu.spring_app.users.model.User;
import iu.iu.spring_app.users.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DeleteUserService {
    private final UserRepository userRepository;
    private final GetUserService getUserService;
    public DeleteUserService(UserRepository userRepository, GetUserService getUserService) {
        this.userRepository = userRepository;
        this.getUserService = getUserService;
    }

    public void deleteUserByEmail(Map<String, String> payload) {
        User user = getUserService.getUserByEmail(payload);
        if (user == null) {
            throw new ResourceNotFoundException("User " + payload.get("email") + " not found");
        }
        userRepository.delete(user);
    }

    public void deleteUserByUsername(Map<String, String> payload) {
        User user = getUserService.getUserByUsername(payload);
        if (user == null) {
            throw new ResourceNotFoundException("User " + payload.get("username") + " not found");
        }
        userRepository.delete(user);
    }
}
