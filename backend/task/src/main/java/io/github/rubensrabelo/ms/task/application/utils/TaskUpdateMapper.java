package io.github.rubensrabelo.ms.task.application.utils;

import io.github.rubensrabelo.ms.task.application.dto.TaskUpdateDTO;
import io.github.rubensrabelo.ms.task.domain.Task;

public class TaskUpdateMapper {
    private TaskUpdateMapper() {
    }

    public static void updateTaskFromDTO(Task entity, TaskUpdateDTO dtoUpdate) {
        if (dtoUpdate.getTitle() != null)
            entity.setTitle(dtoUpdate.getTitle());

        if (dtoUpdate.getDescription() != null)
            entity.setDescription(dtoUpdate.getDescription());

        if (dtoUpdate.getDueDate() != null)
            entity.setDueDate(dtoUpdate.getDueDate());

        if (dtoUpdate.getStatus() != null)
            entity.setStatus(dtoUpdate.getStatus());
    }

}
