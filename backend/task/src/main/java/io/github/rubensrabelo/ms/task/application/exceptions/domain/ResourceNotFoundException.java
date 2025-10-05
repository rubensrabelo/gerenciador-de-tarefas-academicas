package io.github.rubensrabelo.ms.task.application.exceptions.domain;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
