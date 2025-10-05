package io.github.rubensrabelo.ms.task.application.exceptions.handler.resource;

import io.github.rubensrabelo.ms.task.application.exceptions.domain.ResourceNotFoundException;
import io.github.rubensrabelo.ms.task.application.exceptions.dto.StandardError;
import io.github.rubensrabelo.ms.task.application.exceptions.handler.base.BaseExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler extends BaseExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        String error = "Resource not found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        return buildError(status, error, e.getMessage(), request);
    }
}
