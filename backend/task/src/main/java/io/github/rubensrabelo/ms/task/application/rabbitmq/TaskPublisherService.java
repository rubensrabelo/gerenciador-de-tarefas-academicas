package io.github.rubensrabelo.ms.task.application.rabbitmq;

import io.github.rubensrabelo.ms.task.config.RabbitMQConfig;
import io.github.rubensrabelo.ms.task.domain.Task;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class TaskPublisherService {

    private final RabbitTemplate rabbitTemplate;

    public TaskPublisherService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendTaskDelayQueue(Task task) {
        long delayMs = Duration.between(task.getDueDate(), LocalDateTime.now()).toMillis();
        if (delayMs < 0) delayMs = 0;

        long finalDelayMs = delayMs;
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.DELAY_QUEUE,
                task,
                message -> {
                    message.getMessageProperties().setExpiration(String.valueOf(finalDelayMs));
                    return message;
                }
        );
    }
}
