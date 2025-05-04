package iu.iu.spring_app.api.messages.service;

import iu.iu.spring_app.api.messages.model.Message;
import iu.iu.spring_app.api.messages.rabbit.MessageProducer;
import org.springframework.stereotype.Service;

@Service
public class AddMessageService {
    private final MessageProducer messageProducer;

    public AddMessageService(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    public void addMessage(Message message) {
        messageProducer.sendMessage(message);
    }
}
