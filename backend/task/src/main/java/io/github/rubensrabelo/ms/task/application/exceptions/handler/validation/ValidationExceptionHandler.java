package io.github.rubensrabelo.ms.task.application.exceptions.handler.validation;

import io.github.rubensrabelo.ms.task.application.exceptions.dto.StandardError;
import io.github.rubensrabelo.ms.task.application.exceptions.handler.base.BaseExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ValidationExceptionHandler extends BaseExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> handleValidationErrors(
            MethodArgumentNotValidException e, HttpServletRequest request) {

        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError f : e.getBindingResult().getFieldErrors()) {
            fieldErrors.put(f.getField(), f.getDefaultMessage());
        }

        String message = fieldErrors.toString();

        String error = "Validation error";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return buildError(status, error, message, request);
    }
}
