package iu.iu.spring_app;

import iu.iu.spring_app.components.controller.ComponentController;
import iu.iu.spring_app.components.model.Component;
import iu.iu.spring_app.components.service.AddComponentService;
import iu.iu.spring_app.components.service.DeleteComponentService;
import iu.iu.spring_app.components.service.GetComponentService;
import iu.iu.spring_app.components.service.ReplaceComponentService;
import iu.iu.spring_app.errors.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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
    void getAllComponents_EmptyList() {
        when(getComponentService.getAllComponents()).thenReturn(Collections.emptyList());
        assertThrows(ResourceNotFoundException.class, () ->
                componentController.getAllComponents());
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
        when(getComponentService.getComponentById(999)).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () ->
                componentController.getComponent(999));
    }

    @Test
    void addComponent_Success() {
        Component request = new Component();
        request.setName("Test Component");

        Component savedComponent = new Component();
        savedComponent.setId(1);
        savedComponent.setName("Test Component");

        when(addComponentService.addComponent(request)).thenReturn(savedComponent);

        ResponseEntity<Component> response = componentController.addComponent(request);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedComponent, response.getBody());
        assertEquals("/api/components/1", response.getHeaders().getLocation().toString());
    }

    @Test
    void addComponent_NullRequest() {
        assertThrows(ResourceNotFoundException.class, () ->
                componentController.addComponent(null));
    }

    @Test
    void deleteComponent_Success() {
        Map<String, Integer> request = new HashMap<>();
        request.put("componentId", 1);

        ResponseEntity<Component> response = componentController.deleteComponent(request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(deleteComponentService).deleteComponentById(request);
    }

    @Test
    void deleteComponent_NotFound() {
        Map<String, Integer> request = new HashMap<>();
        request.put("componentId", 999);

        doThrow(new ResourceNotFoundException("Component not found"))
                .when(deleteComponentService).deleteComponentById(request);

        assertThrows(ResourceNotFoundException.class, () ->
                componentController.deleteComponent(request));
    }

    @Test
    void deleteComponent_NullRequest() {
        assertThrows(ResourceNotFoundException.class, () ->
                componentController.deleteComponent(null));
    }

    @Test
    void replaceComponent_Success() {
        Component request = new Component();
        request.setId(1);
        request.setName("Updated Component");

        Component updatedComponent = new Component();
        updatedComponent.setId(1);
        updatedComponent.setName("Updated Component");

        when(replaceComponentService.replaceComponent(request)).thenReturn(updatedComponent);

        ResponseEntity<Component> response = componentController.replaceComponent(request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedComponent, response.getBody());
    }

    @Test
    void replaceComponent_NullRequest() {
        assertThrows(ResourceNotFoundException.class, () ->
                componentController.replaceComponent(null));
    }

    @Test
    void replaceComponent_NotFound() {
        Component request = new Component();
        request.setId(999);

        when(replaceComponentService.replaceComponent(request))
                .thenThrow(new ResourceNotFoundException("Component not found"));

        assertThrows(ResourceNotFoundException.class, () ->
                componentController.replaceComponent(request));
    }
}