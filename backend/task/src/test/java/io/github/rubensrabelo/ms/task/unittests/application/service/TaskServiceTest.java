package io.github.rubensrabelo.ms.task.unittests.application.service;

import io.github.rubensrabelo.ms.task.application.TaskService;
import io.github.rubensrabelo.ms.task.application.dto.TaskCreateDTO;
import io.github.rubensrabelo.ms.task.application.dto.TaskResponseDTO;
import io.github.rubensrabelo.ms.task.application.dto.TaskUpdateDTO;
import io.github.rubensrabelo.ms.task.application.exceptions.domain.DatabaseException;
import io.github.rubensrabelo.ms.task.application.exceptions.domain.ResourceNotFoundException;
import io.github.rubensrabelo.ms.task.domain.Task;
import io.github.rubensrabelo.ms.task.domain.enums.TaskStatus;
import io.github.rubensrabelo.ms.task.repository.TaskRepository;
import io.github.rubensrabelo.ms.task.unittests.mocks.MockTaskDTO;
import io.github.rubensrabelo.ms.task.unittests.mocks.MockTaskEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    MockTaskEntity inputEntity;
    MockTaskDTO inputDTO;

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    public void init() throws Exception {
        inputEntity = new MockTaskEntity();
        inputDTO = new MockTaskDTO();
    }

    @Test
    void findAll() {
        List<Task> entities = inputEntity.mockListEntity(5);
        List<TaskResponseDTO> dtos = inputDTO.mockListDTO(5);

        Page<Task> page = new PageImpl<>(entities, PageRequest.of(0, 10), entities.size());

        when(taskRepository.findAll(any(PageRequest.class))).thenReturn(page);
        for (int i = 0; i < entities.size(); i++) {
            when(modelMapper.map(entities.get(i), TaskResponseDTO.class)).thenReturn(dtos.get(i));
        }

        var result = taskService.findAll(PageRequest.of(0, 10));

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(dtos.size(), result.getTotalElements());

        result.forEach(response -> {
            assertNotNull(response);

            assertNotNull(response.getId());
            assertNotNull(response.getTitle());
            assertNotNull(response.getDescription());
            assertNotNull(response.getDueDate());
            assertNotNull(response.getStatus());
        });

        verify(taskRepository, times(1)).findAll(any(PageRequest.class));
        verify(modelMapper, times(5)).map(any(Task.class), eq(TaskResponseDTO.class));
    }

    @Test
    void findById() {
        Task entity = inputEntity.mockEntity(1);
        TaskResponseDTO dtoResponse = inputDTO.mockDTO(1);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(modelMapper.map(entity, TaskResponseDTO.class)).thenReturn(dtoResponse);

        var result = taskService.findById((1L));

        assertNotNull(result);
        assertNotNull(result.getId());

        assertEquals("Title 1", result.getTitle());
        assertEquals("Description 1", result.getDescription());
        assertEquals(entity.getDueDate(), result.getDueDate());
        assertEquals(TaskStatus.COMPLETED, result.getStatus());

        verify(taskRepository, times(1)).findById(1L);
        verify(modelMapper, times(1)).map(entity, TaskResponseDTO.class);
    }

    @Test
    void testFindByIdWithIdDoesNotExist() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(
                ResourceNotFoundException.class,
                () -> taskService.findById(1L)
        );

        String expectedMessage = "Task with id = 1 not found.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(taskRepository, times(1)).findById(1L);
        verifyNoInteractions(modelMapper);
    }

    @Test
    void create() {
        TaskResponseDTO dtoResponse = inputDTO.mockDTO(1);
        Task persisted = inputEntity.mockEntity(1);
        TaskCreateDTO dtoCreate = new TaskCreateDTO(
                dtoResponse.getTitle(),
                dtoResponse.getDescription(),
                dtoResponse.getDueDate()
        );

        when(modelMapper.map(dtoCreate, Task.class)).thenReturn(persisted);
        when(taskRepository.save(persisted)).thenReturn(persisted);
        when(modelMapper.map(persisted, TaskResponseDTO.class)).thenReturn(dtoResponse);

        var result = taskService.create(dtoCreate);

        assertNotNull(result);
        assertNotNull(result.getId());

        assertEquals("Title 1", result.getTitle());
        assertEquals("Description 1", result.getDescription());
        assertEquals(persisted.getDueDate(), result.getDueDate());
        assertEquals(TaskStatus.COMPLETED, result.getStatus());

        verify(modelMapper, times(1)).map(dtoCreate, Task.class);
        verify(taskRepository, times(1)).save(persisted);
        verify(modelMapper, times(1)).map(persisted, TaskResponseDTO.class);
    }

    @Test
    void update() {
        TaskResponseDTO dtoResponse = inputDTO.mockDTO(1);
        Task entity = inputEntity.mockEntity(1);
        TaskUpdateDTO dtoUpdate = new TaskUpdateDTO(
                dtoResponse.getTitle(),
                dtoResponse.getDescription(),
                dtoResponse.getDueDate(),
                dtoResponse.getStatus()
        );

        when(taskRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(taskRepository.save(entity)).thenReturn(entity);
        when(modelMapper.map(entity, TaskResponseDTO.class)).thenReturn(dtoResponse);

        var result = taskService.update(1L, dtoUpdate);

        assertNotNull(result);
        assertNotNull(result.getId());

        assertEquals("Title 1", result.getTitle());
        assertEquals("Description 1", result.getDescription());
        assertEquals(entity.getDueDate(), result.getDueDate());
        assertEquals(TaskStatus.COMPLETED, result.getStatus());

        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(entity);
        verify(modelMapper, times(1)).map(entity, TaskResponseDTO.class);
    }

    @Test
    void testUpdateWithIdDoesNotExist() {
        TaskUpdateDTO dtoUpdate = new TaskUpdateDTO(
                "Updated Task",
                "Description about Updated Task",
                LocalDate.now(),
                TaskStatus.PENDING
        );

        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(
                ResourceNotFoundException.class,
                () -> taskService.update(1L, dtoUpdate)
        );

        String expectedMessage = "Task with id = 1 not found.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(taskRepository, times(1)).findById(1L);
        verifyNoMoreInteractions(taskRepository, modelMapper);
    }

    @Test
    void deleteById() {

        doNothing().when(taskRepository).deleteById(1L);

        assertDoesNotThrow(() -> taskService.delete(1L));
        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteWithIdDoesNotExist() {
        doThrow(new EmptyResultDataAccessException(1)).when(taskRepository).deleteById(1L);

        Exception exception = assertThrows(
                ResourceNotFoundException.class,
                () -> taskService.delete(1L)
        );

        String expectedMessage = "Task with id = 1 not found.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteWithDatabaseException() {
        doThrow(new DataIntegrityViolationException("Database error")).when(taskRepository).deleteById(1L);

        Exception exception = assertThrows(
                DatabaseException.class,
                () -> taskService.delete(1L)
        );

        String expectedMessage = "Database error";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(taskRepository, times(1)).deleteById(1L);
    }
}
