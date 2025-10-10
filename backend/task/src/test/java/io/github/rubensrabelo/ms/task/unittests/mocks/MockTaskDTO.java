package io.github.rubensrabelo.ms.task.unittests.mocks;

import io.github.rubensrabelo.ms.task.application.dto.TaskResponseDTO;
import io.github.rubensrabelo.ms.task.domain.enums.TaskStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MockTaskDTO {

    public TaskResponseDTO mockDTO(Integer id) {
        return createMockDTO(id);
    }

    public List<TaskResponseDTO> mockListDTO(int size) {
        return createMockListDTO(size);
    }

    private List<TaskResponseDTO> createMockListDTO(int size) {
        List<TaskResponseDTO> tasks = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            tasks.add(createMockDTO(i));
        }
        return tasks;
    }

    private TaskResponseDTO createMockDTO(Integer id) {
        TaskResponseDTO entity = new TaskResponseDTO(
                "Title " + id,
                "Description " + id,
                LocalDate.now()
        );
        entity.setId(id.longValue());
        entity.setStatus(chooseTaskStatus((id % 5) + 1));
        return entity;
    }

    private TaskStatus chooseTaskStatus(Integer id) {
        return switch (id) {
            case 1 -> TaskStatus.IN_PROGRESS;
            case 2 -> TaskStatus.COMPLETED;
            case 3 -> TaskStatus.CANCELLED;
            case 4 -> TaskStatus.OVERDUE;
            default -> TaskStatus.PENDING;
        };
    }
}
