package iu.iu.spring_app.api.messages.rabbit;

import iu.iu.spring_app.api.messages.model.Message;
import iu.iu.spring_app.api.messages.repository.MessageRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    private final MessageRepository messageRepository;

    public MessageConsumer(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @RabbitListener(queues = "message-queue")
    public void receiveMessage(Message message)
    {
        messageRepository.save(message);
    }
}