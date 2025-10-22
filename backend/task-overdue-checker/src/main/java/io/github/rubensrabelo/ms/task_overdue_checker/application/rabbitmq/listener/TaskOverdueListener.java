package io.github.rubensrabelo.ms.task_overdue_checker.application.rabbitmq.listener;

import io.github.rubensrabelo.ms.task_overdue_checker.application.rabbitmq.metrics.WorkerMetrics;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TaskOverdueListener {

    private final WorkerMetrics metrics;

    public TaskOverdueListener(WorkerMetrics metrics) {
        this.metrics = metrics;
    }

    @RabbitListener(queues = "task_overdue_queue")
    public void handleTaskOverdue(String message) {
        try {
            System.out.println("Processing message: " + message);

            metrics.incrementProcessed();

        } catch (Exception e) {
            metrics.incrementFailed();
            System.err.println("Error processing message: " + e.getMessage());
        }
    }
}
