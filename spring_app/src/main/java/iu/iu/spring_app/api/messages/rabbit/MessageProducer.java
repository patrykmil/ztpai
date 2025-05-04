package iu.iu.spring_app.api.messages.rabbit;

import iu.iu.spring_app.api.messages.model.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(Message message)
    {
        rabbitTemplate.convertAndSend(
                "message-exchange", "mess", message);
    }
}