package iu.iu.spring_app.api.components.service;

import iu.iu.spring_app.api.components.model.Set;
import iu.iu.spring_app.api.components.repository.SetRepository;
import iu.iu.spring_app.api.errors.ResourceNotFoundException;
import iu.iu.spring_app.api.users.model.User;
import iu.iu.spring_app.api.users.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetService {
    private final SetRepository setRepository;
    private final UserRepository userRepository;

    public SetService(SetRepository setRepository, UserRepository userRepository) {
        this.setRepository = setRepository;
        this.userRepository = userRepository;
    }

    public List<Set> getUserSets(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );
        return setRepository.findByUser(user);
    }

    public Set getSetByName(String name) {
        return setRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Set not found"));
    }

    public Set addUserSet(String setName, String userName) {
        User user = userRepository.findByEmail(userName).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );
        Set newSet = new Set();
        newSet.setName(setName);
        newSet.setUser(user);
        return setRepository.save(newSet);
    }
}
