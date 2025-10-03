package io.github.rubensrabelo.ms.task.application.dto;

import io.github.rubensrabelo.ms.task.domain.enums.TaskStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class TaskUpdateDTO {

    @Size(max = 100, message = "Title must be at most 100 characters")
    private String title;

    @Size(max = 200, message = "Description must be at most 200 characters")
    private String description;

    @FutureOrPresent(message = "Due date cannot be in the past")
    private LocalDate dueDate;

    private TaskStatus status;

    public TaskUpdateDTO() {
    }

    public TaskUpdateDTO(String title, String description, LocalDate dueDate, TaskStatus status) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
