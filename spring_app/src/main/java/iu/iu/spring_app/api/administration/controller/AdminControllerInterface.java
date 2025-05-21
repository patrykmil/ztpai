package iu.iu.spring_app.api.administration.controller;

import io.swagger.v3.oas.annotations.Hidden;
import iu.iu.spring_app.api.administration.model.DeleteComponentModel;
import iu.iu.spring_app.api.components.model.Component;
import iu.iu.spring_app.api.users.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Hidden
@RequestMapping("/api/admin")
public interface AdminControllerInterface {
    @PostMapping("/components/delete/{componentId}")
    ResponseEntity<Component> deleteComponentAdmin(@RequestBody DeleteComponentModel deleteComponentModel);

    @GetMapping("/users/ban/{userId}")
    ResponseEntity<User> banUserAdmin(@PathVariable Integer userId);
}
