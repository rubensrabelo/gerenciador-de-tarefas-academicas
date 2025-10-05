package io.github.rubensrabelo.ms.task.application.exceptions.domain;

public class DatabaseException extends RuntimeException {
    public DatabaseException(String message) {
        super(message);
    }
}
