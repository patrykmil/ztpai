package iu.iu.spring_app;

import iu.iu.spring_app.api.components.controller.ComponentController;
import iu.iu.spring_app.api.components.model.Component;
import iu.iu.spring_app.api.components.model.ComponentFilter;
import iu.iu.spring_app.api.components.service.AddComponentService;
import iu.iu.spring_app.api.components.service.DeleteComponentService;
import iu.iu.spring_app.api.components.service.GetComponentService;
import iu.iu.spring_app.api.components.service.ReplaceComponentService;
import iu.iu.spring_app.api.errors.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ComponentControllerTests {

    @Mock
    private GetComponentService getComponentService;
    @Mock
    private AddComponentService addComponentService;
    @Mock
    private ReplaceComponentService replaceComponentService;
    @Mock
    private DeleteComponentService deleteComponentService;
    @Mock
    private Authentication authentication;

    @InjectMocks
    private ComponentController componentController;

    @Test
    void getAllComponents_Success() {
        List<Component> components = Arrays.asList(
                new Component(), new Component()
        );
        when(getComponentService.getAllComponents()).thenReturn(components);

        ResponseEntity<List<Component>> response = componentController.getAllComponents();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(components, response.getBody());
    }

    @Test
    void getComponent_Success() {
        Component component = new Component();
        component.setId(1);
        when(getComponentService.getComponentById(1)).thenReturn(component);

        ResponseEntity<Component> response = componentController.getComponent(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(component, response.getBody());
    }

    @Test
    void getComponent_NotFound() {
        when(getComponentService.getComponentById(999))
                .thenThrow(new ResourceNotFoundException("Component not found"));

        assertThrows(ResourceNotFoundException.class, () -> componentController.getComponent(999));
    }

    @Test
    void addComponent_Success() {
        Component request = new Component();
        request.setName("Test Component");

        Component savedComponent = new Component();
        savedComponent.setId(1);
        savedComponent.setName("Test Component");

        when(authentication.getName()).thenReturn("test@test.com");
        when(addComponentService.addComponent(request, "test@test.com")).thenReturn(savedComponent);

        ResponseEntity<Component> response = componentController.addComponent(request, authentication);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedComponent, response.getBody());
        assertEquals("/api/components/1", response.getHeaders().getLocation().toString());
    }

    @Test
    void addComponent_NullRequest() {
        assertThrows(ResourceNotFoundException.class, () ->
                componentController.addComponent(null, authentication));
    }

    @Test
    void deleteComponent_Success() {
        Integer request = 1;

        when(authentication.getName()).thenReturn("test@test.com");

        ResponseEntity<Component> response = componentController.deleteComponent(request, authentication);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(deleteComponentService).deleteComponentById(1, "test@test.com");
    }

    @Test
    void replaceComponent_Success() {
        Component request = new Component();
        request.setId(1);
        request.setName("Updated Component");

        Component updatedComponent = new Component();
        updatedComponent.setId(1);
        updatedComponent.setName("Updated Component");

        when(authentication.getName()).thenReturn("test@test.com");
        when(replaceComponentService.replaceComponent(request, "test@test.com")).thenReturn(updatedComponent);

        ResponseEntity<Component> response = componentController.replaceComponent(request, authentication);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedComponent, response.getBody());
    }

    @Test
    void searchComponents_Success() {
        ComponentFilter filter = new ComponentFilter();
        List<Component> components = Arrays.asList(new Component(), new Component());
        when(getComponentService.getFilteredComponents(filter)).thenReturn(components);

        ResponseEntity<List<Component>> response = componentController.searchComponents(filter);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(components, response.getBody());
    }

    @Test
    void searchComponents_EmptyResult() {
        ComponentFilter filter = new ComponentFilter();
        when(getComponentService.getFilteredComponents(filter)).thenReturn(Collections.emptyList());

        ResponseEntity<List<Component>> response = componentController.searchComponents(filter);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.emptyList(), response.getBody());
    }

    @Test
    void getLikedComponents_Success() {
        List<Component> components = Arrays.asList(new Component(), new Component());
        when(getComponentService.getLikedByUserComponents("test")).thenReturn(components);

        ResponseEntity<List<Component>> response = componentController.getLikedComponents("test");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(components, response.getBody());
    }

    @Test
    void getLikedComponents_EmptyList() {
        when(getComponentService.getLikedByUserComponents("test")).thenReturn(Collections.emptyList());

        ResponseEntity<List<Component>> response = componentController.getLikedComponents("test");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.emptyList(), response.getBody());
    }

    @Test
    void getSetComponents_Success() {
        List<Component> components = Arrays.asList(new Component(), new Component());
        when(getComponentService.getSetComponents(1)).thenReturn(components);

        ResponseEntity<List<Component>> response = componentController.getSetComponents(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(components, response.getBody());
    }

    @Test
    void getSetComponents_EmptySet() {
        when(getComponentService.getSetComponents(1)).thenReturn(Collections.emptyList());

        ResponseEntity<List<Component>> response = componentController.getSetComponents(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.emptyList(), response.getBody());
    }

    @Test
    void replaceComponent_NullRequest() {
        assertThrows(ResourceNotFoundException.class, () ->
                componentController.replaceComponent(null, authentication));
    }

}