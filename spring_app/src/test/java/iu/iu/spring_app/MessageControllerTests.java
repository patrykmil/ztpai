package iu.iu.spring_app;

import iu.iu.spring_app.api.messages.controller.MessageController;
import iu.iu.spring_app.api.messages.model.Message;
import iu.iu.spring_app.api.messages.service.AddMessageService;
import iu.iu.spring_app.api.messages.service.GetMessageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageControllerTests {

    @Mock
    private GetMessageService getMessageService;
    @Mock
    private AddMessageService addMessageService;
    @Mock
    private Authentication authentication;

    @InjectMocks
    private MessageController messageController;

    @Test
    void getUserMessages_Success() {
        List<Message> messages = Arrays.asList(new Message(), new Message());
        when(authentication.getName()).thenReturn("test@test.com");
        when(getMessageService.getUserMessages("test@test.com")).thenReturn(messages);

        ResponseEntity<List<Message>> response = messageController.getUserMessages(authentication);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(messages, response.getBody());
    }

    @Test
    void addMessage_Success() {
        Message message = new Message();
        doNothing().when(addMessageService).addMessage(message);

        ResponseEntity<Message> response = messageController.addMessage(message);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(addMessageService).addMessage(message);
    }
}