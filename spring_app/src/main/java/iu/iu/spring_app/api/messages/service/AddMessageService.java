package iu.iu.spring_app.api.messages.service;

import iu.iu.spring_app.api.messages.model.Message;
import iu.iu.spring_app.api.messages.rabbit.MessageProducer;
import iu.iu.spring_app.api.messages.repository.MessageRepository;
import iu.iu.spring_app.api.users.service.GetUserService;
import org.springframework.stereotype.Service;

@Service
public class AddMessageService {
    private final MessageRepository messageRepository;
    private final GetUserService getUserService;
    private final MessageProducer messageProducer;

    public AddMessageService(MessageRepository messageRepository, GetUserService getUserService, MessageProducer messageProducer) {
        this.messageRepository = messageRepository;
        this.getUserService = getUserService;
        this.messageProducer = messageProducer;
    }

    public void addMessage(Message message) {
        messageProducer.sendMessage(message);
    }
}
