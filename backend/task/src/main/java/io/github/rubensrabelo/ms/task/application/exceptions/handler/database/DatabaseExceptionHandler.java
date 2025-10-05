package io.github.rubensrabelo.ms.task.application.exceptions.handler.database;

import io.github.rubensrabelo.ms.task.application.exceptions.domain.DatabaseException;
import io.github.rubensrabelo.ms.task.application.exceptions.dto.StandardError;
import io.github.rubensrabelo.ms.task.application.exceptions.handler.base.ErrorBuilder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DatabaseExceptionHandler {

    private final ErrorBuilder errorBuilder;

    public DatabaseExceptionHandler(ErrorBuilder errorBuilder) {
        this.errorBuilder = errorBuilder;
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardError> handleDatabaseException(
            DatabaseException e, HttpServletRequest request) {
        String error = "Database error";
        HttpStatus status = HttpStatus.CONFLICT;
        return errorBuilder.build(status, error, e.getMessage(), request);
    }
}
