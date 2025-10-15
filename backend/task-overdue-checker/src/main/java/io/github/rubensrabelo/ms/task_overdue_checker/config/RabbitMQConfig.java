package io.github.rubensrabelo.ms.task_overdue_checker.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String OVERDUE_QUEUE = "task_overdue_queue";

    @Bean
    public Queue getOverdueQueue() {
        return QueueBuilder.durable(OVERDUE_QUEUE).build();
    }
}
