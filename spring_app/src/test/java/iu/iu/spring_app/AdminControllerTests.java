package iu.iu.spring_app;

import iu.iu.spring_app.api.administration.controller.AdminController;
import iu.iu.spring_app.api.administration.model.DeleteComponentModel;
import iu.iu.spring_app.api.administration.service.AdminService;
import iu.iu.spring_app.api.components.model.Component;
import iu.iu.spring_app.api.users.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AdminControllerTests {

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;

    @Test
    void deleteComponentAdmin_Success() {
        DeleteComponentModel deleteModel = new DeleteComponentModel();
        doNothing().when(adminService).removeComponent(deleteModel);

        ResponseEntity<Component> response = adminController.deleteComponentAdmin(deleteModel);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(adminService).removeComponent(deleteModel);
    }

    @Test
    void banUserAdmin_Success() {
        Integer userId = 1;
        doNothing().when(adminService).banUser(userId);

        ResponseEntity<User> response = adminController.banUserAdmin(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(adminService).banUser(userId);
    }
}