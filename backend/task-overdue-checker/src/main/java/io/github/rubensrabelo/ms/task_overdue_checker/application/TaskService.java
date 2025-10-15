package io.github.rubensrabelo.ms.task_overdue_checker.application;

import io.github.rubensrabelo.ms.task_overdue_checker.domain.Task;
import io.github.rubensrabelo.ms.task_overdue_checker.domain.enums.TaskStatus;
import io.github.rubensrabelo.ms.task_overdue_checker.repository.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void markTaskOverdue(Task task) {
        taskRepository.findById(task.getId()).ifPresent(
                entity -> {
                    entity.setStatus(TaskStatus.OVERDUE);
                    taskRepository.save(entity);
                }
        );
    }
}
