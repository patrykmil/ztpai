package iu.iu.spring_app;

import iu.iu.spring_app.errors.ResourceNotFoundException;
import iu.iu.spring_app.users.controller.UserController;
import iu.iu.spring_app.users.model.User;
import iu.iu.spring_app.users.service.AuthenticationService;
import iu.iu.spring_app.users.service.GetUserService;
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
    private AuthenticationService authenticationService;

    @InjectMocks
    private UserController userController;

    private User testUser;
    private List<User> userList;
    private Map<String, String> loginPayload;
    private Map<String, String> registerPayload;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(99);
        testUser.setEmail("test@test.com");
        testUser.setUsername("testuser");
        testUser.setPassword("password");

        userList = new ArrayList<>();
        userList.add(testUser);

        loginPayload = new HashMap<>();
        loginPayload.put("email", "test@test.com");
        loginPayload.put("password", "password");

        registerPayload = new HashMap<>();
        registerPayload.put("email", "test@test.com");
        registerPayload.put("password", "password");
        registerPayload.put("username", "testuser");
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
        when(getUserService.getUserById(99)).thenReturn(testUser);
        ResponseEntity<User> response = userController.getUserById(99);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testUser, response.getBody());
    }

    @Test
    void getUserById_NotFound() {
        when(getUserService.getUserById(999)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> userController.getUserById(999));
    }

    @Test
    void getUserByEmail_Success() {
        when(getUserService.getUserByEmail(any())).thenReturn(testUser);
        ResponseEntity<User> response = userController.getUserByEmail(loginPayload);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testUser, response.getBody());
    }

    @Test
    void getUserByEmail_NotFound() {
        when(getUserService.getUserByEmail(any())).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> userController.getUserByEmail(loginPayload));
    }

    @Test
    void register_Success() {
        when(authenticationService.register(any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CREATED));

        ResponseEntity<?> response = userController.register(registerPayload);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void register_MissingFields() {
        Map<String, String> invalidPayload = new HashMap<>();
        invalidPayload.put("email", "test@test.com");

        assertThrows(ResourceNotFoundException.class, () -> userController.register(invalidPayload));
    }

    @Test
    void login_Success() {
        when(authenticationService.login(any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        ResponseEntity<?> response = userController.login(loginPayload);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void login_MissingFields() {
        Map<String, String> invalidPayload = new HashMap<>();
        invalidPayload.put("email", "test@test.com");

        assertThrows(ResourceNotFoundException.class, () -> userController.login(invalidPayload));
    }
}
