package io.github.rubensrabelo.ms.task.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String DELAY_QUEUE = "task_delay_queue";
    public static final String OVERDUE_QUEUE = "task_overdue_queue";
    public static final String DLX_EXCHANGE = "task_dlx_exchange";

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue delayQueue() {
        return QueueBuilder.durable(DELAY_QUEUE)
                .withArgument("x-dead-letter-exchange", DLX_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", OVERDUE_QUEUE)
                .build();
    }

    @Bean
    public Queue overdueQueue() {
        return QueueBuilder.durable(OVERDUE_QUEUE).build();
    }

    @Bean
    public DirectExchange dlxExchange() {
        return new DirectExchange(DLX_EXCHANGE);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(overdueQueue())
                .to(dlxExchange())
                .with(OVERDUE_QUEUE);
    }
}
