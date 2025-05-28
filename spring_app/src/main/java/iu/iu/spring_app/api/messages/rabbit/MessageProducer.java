package iu.iu.spring_app.api.messages.rabbit;

import iu.iu.spring_app.api.messages.model.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {
    private final RabbitTemplate rabbitTemplate;

    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(Message message) {
        rabbitTemplate.convertAndSend("message-exchange", "mess", message);
    }
}