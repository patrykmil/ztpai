package iu.iu.spring_app.api.messages.rabbit;

import iu.iu.spring_app.api.messages.model.Message;
import iu.iu.spring_app.api.messages.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageConsumer {

    private final MessageRepository messageRepository;

    public MessageConsumer(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @RabbitListener(queues = "message-queue")
    public void receiveMessage(Message message)
    {
        try {
            messageRepository.save(message);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}