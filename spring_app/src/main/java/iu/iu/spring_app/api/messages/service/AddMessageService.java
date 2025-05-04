package iu.iu.spring_app.api.messages.service;

import iu.iu.spring_app.api.messages.model.Message;
import iu.iu.spring_app.api.messages.repository.MessageRepository;
import iu.iu.spring_app.api.users.service.GetUserService;
import org.springframework.stereotype.Service;

@Service
public class AddMessageService {
    private final MessageRepository messageRepository;
    private final GetUserService getUserService;

    public AddMessageService(MessageRepository messageRepository, GetUserService getUserService) {
        this.messageRepository = messageRepository;
        this.getUserService = getUserService;
    }

    public Message addMessage(Message message) {
        return messageRepository.save(message);
    }
}
