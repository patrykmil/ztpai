package iu.iu.spring_app.api.messages.controller;

import iu.iu.spring_app.api.messages.model.Message;
import iu.iu.spring_app.api.messages.service.AddMessageService;
import iu.iu.spring_app.api.messages.service.GetMessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessageController implements MessageControllerInterface {
    private final GetMessageService getMessageService;
    private final AddMessageService addMessageService;

    public MessageController(GetMessageService getMessageService, AddMessageService addMessageService) {
        this.getMessageService = getMessageService;
        this.addMessageService = addMessageService;
    }

    public ResponseEntity<List<Message>> getUserMessages(Authentication authentication) {
        return ResponseEntity.ok(getMessageService.getUserMessages(authentication.getName()));
    }

    public ResponseEntity<Message> addMessage(@RequestBody Message payload) {
        return ResponseEntity.ok(addMessageService.addMessage(payload));
    }
}
