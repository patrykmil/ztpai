package iu.iu.spring_app;

import iu.iu.spring_app.api.components.controller.ComponentPropertiesController;
import iu.iu.spring_app.api.components.model.Set;
import iu.iu.spring_app.api.components.model.Tag;
import iu.iu.spring_app.api.components.model.Type;
import iu.iu.spring_app.api.components.service.SetService;
import iu.iu.spring_app.api.components.service.TagsService;
import iu.iu.spring_app.api.components.service.TypeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ComponentPropertiesControllerTests {

    @Mock
    private SetService setService;
    @Mock
    private TypeService typeService;
    @Mock
    private TagsService tagsService;
    @Mock
    private Authentication authentication;

    @InjectMocks
    private ComponentPropertiesController componentPropertiesController;

    @Test
    void getAllTags_Success() {
        List<Tag> tags = Arrays.asList(new Tag(), new Tag());
        when(tagsService.getTags()).thenReturn(tags);

        ResponseEntity<List<Tag>> response = componentPropertiesController.getAllTags();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tags, response.getBody());
    }

    @Test
    void getAllTypes_Success() {
        List<Type> types = Arrays.asList(new Type(), new Type());
        when(typeService.getTypes()).thenReturn(types);

        ResponseEntity<List<Type>> response = componentPropertiesController.getAllTypes();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(types, response.getBody());
    }

    @Test
    void getAllSetsById_Success() {
        List<Set> sets = Arrays.asList(new Set(), new Set());
        when(setService.getUserSetsById(1)).thenReturn(sets);

        ResponseEntity<List<Set>> response = componentPropertiesController.getAllSetsById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sets, response.getBody());
    }

    @Test
    void getAllSetsByName_Success() {
        List<Set> sets = Arrays.asList(new Set(), new Set());
        when(setService.getUserSetsByName("test")).thenReturn(sets);

        ResponseEntity<List<Set>> response = componentPropertiesController.getAllSetsByName("test");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sets, response.getBody());
    }

    @Test
    void addSet_Success() {
        Set newSet = new Set();
        newSet.setId(1);
        newSet.setName("Test Set");

        Map<String, String> payload = new HashMap<>();
        payload.put("setName", "Test Set");

        when(authentication.getName()).thenReturn("test@test.com");
        when(setService.addUserSet("Test Set", "test@test.com")).thenReturn(newSet);

        ResponseEntity<Set> response = componentPropertiesController.addSet(payload, authentication);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(newSet, response.getBody());
    }
}