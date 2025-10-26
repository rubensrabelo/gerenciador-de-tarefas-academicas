package io.github.rubensrabelo.ms.task.application;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.rubensrabelo.ms.task.application.dto.TaskCreateDTO;
import io.github.rubensrabelo.ms.task.application.dto.TaskResponseDTO;
import io.github.rubensrabelo.ms.task.application.dto.TaskUpdateDTO;
import io.github.rubensrabelo.ms.task.application.resilience4j.NotificationFallbackHandler;
import io.github.rubensrabelo.ms.task.infra.feign.NotificationClient;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/v1/tasks")
public class TaskController {

    private final TaskService taskService;
    private final NotificationClient notificationClient;
    private final NotificationFallbackHandler fallbackHandler;

    public TaskController(
            TaskService taskService,
            NotificationClient notificationClient,
            NotificationFallbackHandler fallbackHandler
            ) {
        this.taskService = taskService;
        this.notificationClient = notificationClient;
        this.fallbackHandler = fallbackHandler;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<TaskResponseDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        Direction sortDirection = direction.equalsIgnoreCase("asc") ? Direction.ASC : Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "dueDate"));
        Page<TaskResponseDTO> dto = taskService.findAll(pageable);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TaskResponseDTO> findById(
            @PathVariable Long id
    ) {
        TaskResponseDTO dto = taskService.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @CircuitBreaker(name = "msnotification", fallbackMethod = "fallback")
    @Retry(name = "msnotification")
    public ResponseEntity<TaskResponseDTO> create(
            @Valid @RequestBody TaskCreateDTO taskCreateDTO,
            @RequestParam String email
    ) {
        TaskResponseDTO dto = taskService.create(taskCreateDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        notificationClient.send(
                "New task created: " + dto.getTitle(),
                email
        );
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(
            value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<TaskResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody TaskUpdateDTO taskUpdateDTO
    ) {
        TaskResponseDTO dto = taskService.update(id, taskUpdateDTO);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<TaskResponseDTO> fallback(TaskResponseDTO dto, String email, Throwable t) {
        String msg = fallbackHandler.handle(dto.getTitle(), email, t);
        return ResponseEntity.internalServerError().body(dto);
    }
}
