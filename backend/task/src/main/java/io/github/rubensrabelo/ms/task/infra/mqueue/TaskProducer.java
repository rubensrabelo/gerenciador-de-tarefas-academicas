package io.github.rubensrabelo.ms.task.infra.mqueue;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class TaskProducer {

    private final RabbitTemplate rabbitTemplate;
    private static final String EXCHANGE = "task.exchange";
    private static final String ROUTING_KEY = "task.created";

    public TaskProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendTaskCreated(Long taskId, String dueDate) {
        String message = taskId + "," + dueDate;
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, message);
        System.out.println("[RabbitMQ] Sent: " + message);
    }
}
