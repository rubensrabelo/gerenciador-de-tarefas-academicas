package io.github.rubensrabelo.ms.task.application.rabbitmq;

import io.github.rubensrabelo.ms.task.config.RabbitMQConfig;
import io.github.rubensrabelo.ms.task.domain.Task;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class TaskPublisherService {

    private final RabbitTemplate rabbitTemplate;

    public TaskPublisherService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendTaskDelayQueue(Task task) {
        ZoneId zone = ZoneId.of("America/Sao_Paulo");
        LocalDateTime now = LocalDateTime.now(zone);

        long delayMs = Duration.between(now, task.getDueDate()).toMillis();
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
