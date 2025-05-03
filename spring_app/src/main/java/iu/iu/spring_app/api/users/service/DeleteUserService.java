package iu.iu.spring_app.api.users.service;

import iu.iu.spring_app.api.users.model.User;
import iu.iu.spring_app.api.users.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteUserService {
    private final UserRepository userRepository;
    private final GetUserService getUserService;
    public DeleteUserService(UserRepository userRepository, GetUserService getUserService) {
        this.userRepository = userRepository;
        this.getUserService = getUserService;
    }

    public void deleteUserByEmail(String email) {
        User user = getUserService.getUserByEmail(email);
        userRepository.delete(user);
    }

    public void deleteUserByUsername(String username) {
        User user = getUserService.getUserByUsername(username);
        userRepository.delete(user);
    }
}
