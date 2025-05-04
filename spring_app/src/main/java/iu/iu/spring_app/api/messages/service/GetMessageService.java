package iu.iu.spring_app.api.messages.service;

import iu.iu.spring_app.api.messages.model.Message;
import iu.iu.spring_app.api.messages.repository.MessageRepository;
import iu.iu.spring_app.api.users.model.User;
import iu.iu.spring_app.api.users.service.GetUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetMessageService {
    private final MessageRepository messageRepository;
    private final GetUserService getUserService;

    public GetMessageService(MessageRepository messageRepository, GetUserService getUserService) {
        this.messageRepository = messageRepository;
        this.getUserService = getUserService;
    }

    public List<Message> getUserMessages(String email) {
        User user = getUserService.getUserByEmail(email);
        return messageRepository.findByUser(user);
    }
}
