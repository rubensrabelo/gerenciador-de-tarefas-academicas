package io.github.rubensrabelo.ms.task.unittests.mocks;

import io.github.rubensrabelo.ms.task.domain.Task;
import io.github.rubensrabelo.ms.task.domain.enums.TaskStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MockTaskEntity {

    public Task mockEntity(Integer id) {
        return createMockEntity(id);
    }

    public List<Task> mockListEntity(int size) {
        return createMockListEntity(size);
    }

    private List<Task> createMockListEntity(int size) {
        List<Task> tasks = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            tasks.add(createMockEntity(i));
        }
        return tasks;
    }

    private Task createMockEntity(Integer id) {
        Task entity = new Task(
                "Title " + id,
                "Description " + id,
                LocalDateTime.now()
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
