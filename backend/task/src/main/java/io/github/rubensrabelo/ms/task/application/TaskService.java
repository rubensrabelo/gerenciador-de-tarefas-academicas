package io.github.rubensrabelo.ms.task.application;

import io.github.rubensrabelo.ms.task.application.dto.TaskResponseDTO;
import io.github.rubensrabelo.ms.task.application.exceptions.domain.ResourceNotFoundException;
import io.github.rubensrabelo.ms.task.domain.Task;
import io.github.rubensrabelo.ms.task.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private TaskRepository taskRepository;
    private ModelMapper modelMapper;

    public TaskService(TaskRepository taskRepository, ModelMapper modelMapper) {
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
    }

    public Page<TaskResponseDTO> findAll(Pageable pageable) {
        Page<Task> entities = taskRepository.findAll(pageable);
        return entities.map(
                task -> modelMapper.map(task, TaskResponseDTO.class)
        );
    }

    public TaskResponseDTO findById(Long id) {
        Task entity = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id = " + id + " not found."));
        return modelMapper.map(entity, TaskResponseDTO.class);
    }

    public TaskResponseDTO create() {
        return null;
    }

    public TaskResponseDTO update() {
        return null;
    }

    public void delete() {
    }
}
