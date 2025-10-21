package io.github.rubensrabelo.ms.task_overdue_checker.application.rabbitmq.actuator.health;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class RabbitHealthIndicator implements HealthIndicator {

    private final ConnectionFactory connectionFactory;

    public RabbitHealthIndicator(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public Health health() {
        try (Connection conn = connectionFactory.newConnection()) {
            return Health.up().withDetail("RabbitMQ", "Connected").build();
        } catch (Exception e) {
            return Health.down(e).withDetail("RabbitMQ", "Connection failed").build();
        }
    }
}
