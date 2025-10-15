package io.github.rubensrabelo.ms.task.application;

import io.github.rubensrabelo.ms.task.application.dto.TaskCreateDTO;
import io.github.rubensrabelo.ms.task.application.dto.TaskResponseDTO;
import io.github.rubensrabelo.ms.task.application.dto.TaskUpdateDTO;
import io.github.rubensrabelo.ms.task.application.exceptions.domain.DatabaseException;
import io.github.rubensrabelo.ms.task.application.exceptions.domain.ResourceNotFoundException;
import io.github.rubensrabelo.ms.task.application.rabbitmq.TaskPublisherService;
import io.github.rubensrabelo.ms.task.domain.Task;
import io.github.rubensrabelo.ms.task.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static io.github.rubensrabelo.ms.task.application.utils.TaskUpdateMapper.updateTaskFromDTO;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;
    private final TaskPublisherService taskProducer;

    public TaskService(
            TaskRepository taskRepository,
            ModelMapper modelMapper,
            TaskPublisherService taskProducer
    ) {
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
        this.taskProducer = taskProducer;
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

    public TaskResponseDTO create(TaskCreateDTO dtoCreate) {
        Task entity = modelMapper.map(dtoCreate, Task.class);
        entity = taskRepository.save(entity);
        taskProducer.sendTaskDelayQueue(entity);
        return modelMapper.map(entity, TaskResponseDTO.class);
    }

    public TaskResponseDTO update(Long id, TaskUpdateDTO dtoUpdate) {
        Task entity = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id = " + id + " not found."));
        updateTaskFromDTO(entity, dtoUpdate);
        entity = taskRepository.save(entity);
        taskProducer.sendTaskDelayQueue(entity);
        return modelMapper.map(entity, TaskResponseDTO.class);
    }

    public void delete(Long id) {
        try {
            taskRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Task with id = " + id + " not found.");
        }  catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}
