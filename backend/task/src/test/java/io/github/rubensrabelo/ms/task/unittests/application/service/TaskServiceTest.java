package io.github.rubensrabelo.ms.task.unittests.application.service;

import io.github.rubensrabelo.ms.task.application.TaskService;
import io.github.rubensrabelo.ms.task.application.dto.TaskResponseDTO;
import io.github.rubensrabelo.ms.task.domain.Task;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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
}
