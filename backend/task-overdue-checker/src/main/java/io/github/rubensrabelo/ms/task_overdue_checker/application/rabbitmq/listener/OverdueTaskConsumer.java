package io.github.rubensrabelo.ms.task_overdue_checker.application.rabbitmq.listener;

import io.github.rubensrabelo.ms.task_overdue_checker.application.TaskService;
import io.github.rubensrabelo.ms.task_overdue_checker.config.RabbitMQConfig;
import io.github.rubensrabelo.ms.task_overdue_checker.domain.Task;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OverdueTaskConsumer {

    private final TaskService taskService;

    public OverdueTaskConsumer(TaskService taskService) {
        this.taskService = taskService;
    }

    @RabbitListener(queues = RabbitMQConfig.OVERDUE_QUEUE)
    public void handleOverdueTask(Task task) {
        System.out.println("Received overdue task: " + task.getId());
        taskService.markTaskOverdue(task);
    }
}
