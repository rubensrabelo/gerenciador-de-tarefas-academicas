package io.github.rubensrabelo.ms.task.application.exceptions.handler.base;

import io.github.rubensrabelo.ms.task.application.exceptions.dto.StandardError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ErrorBuilder {

    public ResponseEntity<StandardError> build(
            HttpStatus status,
            String error,
            String message,
            HttpServletRequest request
    ) {
        StandardError err = new StandardError(
                Instant.now(),
                status.value(),
                error,
                message,
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(err);
    }
}
