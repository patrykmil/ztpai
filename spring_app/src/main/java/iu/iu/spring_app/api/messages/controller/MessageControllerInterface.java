package iu.iu.spring_app.api.messages.controller;

import iu.iu.spring_app.api.messages.model.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/messages")
public interface MessageControllerInterface {
    @GetMapping("/get/my")
    ResponseEntity<List<Message>> getUserMessages(Authentication authentication);

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Message> addMessage(@RequestBody Message payload);
}
