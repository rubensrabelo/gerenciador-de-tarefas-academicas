package io.github.rubensrabelo.ms.task.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String TASK_STATUS_QUEUE = "task.status.queue";
    public static final String TASK_STATUS_EXCHANGE = "task.status.exchange";
    public static final String TASK_STATUS_ROUTING_KEY = "task.status.#";

    @Bean
    public TopicExchange getTaskStatusExchange() {
        return new TopicExchange(TASK_STATUS_EXCHANGE);
    }

    @Bean
    public Queue getTaskStatusQueue() {
        return new Queue(TASK_STATUS_QUEUE, true);
    }

    @Bean
    public Binding getTaskStatusBinding(Queue taskStatusQueue, TopicExchange taskStatusExchange) {
        return BindingBuilder.bind(taskStatusQueue).to(taskStatusExchange).with(TASK_STATUS_ROUTING_KEY);
    }
}
