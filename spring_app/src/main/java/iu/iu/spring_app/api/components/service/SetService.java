package iu.iu.spring_app.api.components.service;

import iu.iu.spring_app.api.components.model.Set;
import iu.iu.spring_app.api.components.repository.SetRepository;
import iu.iu.spring_app.api.errors.ResourceNotFoundException;
import iu.iu.spring_app.api.users.model.User;
import iu.iu.spring_app.api.users.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SetService {
    private final SetRepository setRepository;
    private final UserRepository userRepository;

    public SetService(SetRepository setRepository, UserRepository userRepository) {
        this.setRepository = setRepository;
        this.userRepository = userRepository;
    }

    public List<Set> getUserSetsByUserId(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );
        return setRepository.findByUser(user);
    }

    public List<Set> getUserSetsByUsername(String username) {
        User user = userRepository.findByName(username).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );
        return setRepository.findByUser(user);
    }

    public Set getSetByName(String setName, User user) {
        return setRepository.findByNameAndUser(setName, user)
                .orElseThrow(() -> new ResourceNotFoundException("Set not found"));
    }

    public Set addUserSet(String setName, String userName) {
        User user = userRepository.findByEmail(userName).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );

        Optional<Set> set = setRepository.findByNameAndUser(setName, user);
        if (set.isPresent()) {
            throw new RuntimeException("Set with that name already exists");
        }

        Set newSet = Set.builder()
                .name(setName)
                .user(user)
                .build();
        return setRepository.save(newSet);
    }
}
