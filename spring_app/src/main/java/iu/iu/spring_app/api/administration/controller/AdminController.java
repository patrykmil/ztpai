package iu.iu.spring_app.api.administration.controller;

import iu.iu.spring_app.api.administration.model.DeleteComponentModel;
import iu.iu.spring_app.api.administration.service.AdminService;
import iu.iu.spring_app.api.components.model.Component;
import iu.iu.spring_app.api.users.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasRole('ADMIN')")
public class AdminController implements AdminControllerInterface {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public ResponseEntity<Component> deleteComponentAdmin(DeleteComponentModel deleteComponent) {
        adminService.removeComponent(deleteComponent);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<User> banUserAdmin(Integer userId) {
        adminService.banUser(userId);
        return ResponseEntity.ok().build();
    }
}
