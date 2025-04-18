package iu.iu.spring_app.api.security.service;

import iu.iu.spring_app.api.users.model.User;
import iu.iu.spring_app.api.users.repository.AvatarRepository;
import iu.iu.spring_app.api.users.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final AvatarRepository avatarRepository;

    public UserService(UserRepository userRepository, AvatarRepository avatarRepository) {
        this.userRepository = userRepository;
        this.avatarRepository = avatarRepository;
    }

    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new org.springframework.security.core.userdetails.UsernameNotFoundException("User not found"));
    }

    public User save(User user) {
        user.setAvatar(avatarRepository.findRandom());
        return userRepository.save(user);
    }
}