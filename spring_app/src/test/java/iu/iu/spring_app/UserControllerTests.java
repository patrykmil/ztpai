package iu.iu.spring_app;

import iu.iu.spring_app.api.errors.ResourceNotFoundException;
import iu.iu.spring_app.api.users.controller.UserController;
import iu.iu.spring_app.api.users.model.User;
import iu.iu.spring_app.api.users.service.DeleteUserService;
import iu.iu.spring_app.api.users.service.GetUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTests {
    @Mock
    private GetUserService getUserService;

    @Mock
    private DeleteUserService deleteUserService;

    @InjectMocks
    private UserController userController;


    private User testUser;
    private List<User> userList;
    private Map<String, String> userInfo;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1);
        testUser.setEmail("test@test.com");
        testUser.setName("testuser");
        testUser.setPassword("password");

        userList = new ArrayList<>();
        userList.add(testUser);

        userInfo = new HashMap<>();
        userInfo.put("email", "test@test.com");
        userInfo.put("password", "password");
    }

    @Test
    void getAllUsers_Success() {
        when(getUserService.getAllUsers()).thenReturn(userList);
        ResponseEntity<List<User>> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userList, response.getBody());
    }

    @Test
    void getAllUsers_EmptyList() {
        when(getUserService.getAllUsers()).thenReturn(new ArrayList<>());

        assertThrows(ResourceNotFoundException.class, () -> userController.getAllUsers());
    }

    @Test
    void getUserById_Success() {
        when(getUserService.getUserById(1)).thenReturn(testUser);
        ResponseEntity<User> response = userController.getUserById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testUser, response.getBody());
    }

    @Test
    void getUserByEmail_Success() {
        when(getUserService.getUserByEmail(any())).thenReturn(testUser);
        ResponseEntity<User> response = userController.getUserByEmail(userInfo);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testUser, response.getBody());
    }

    @Test
    void getUserByUsername_Success() {
        when(getUserService.getUserByUsername(any())).thenReturn(testUser);
        ResponseEntity<User> response = userController.getUserByUsername(userInfo);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(testUser, response.getBody());
    }

    @Test
    void deleteByEmail_Success() {
        Map<String, String> payload = new HashMap<>();
        payload.put("email", "test@test.com");

        ResponseEntity<?> response = userController.deleteByEmail(payload);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteByEmail_NotFound() {
        Map<String, String> payload = new HashMap<>();
        payload.put("email", "notfound@test.com");

        org.mockito.Mockito.doThrow(new ResourceNotFoundException("User not found"))
                .when(deleteUserService).deleteUserByEmail(payload.get("email"));

        assertThrows(ResourceNotFoundException.class, () -> userController.deleteByEmail(payload));
    }

    @Test
    void deleteByUsername_Success() {
        Map<String, String> payload = new HashMap<>();
        payload.put("username", "testuser");

        ResponseEntity<?> response = userController.deleteByUsername(payload);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
