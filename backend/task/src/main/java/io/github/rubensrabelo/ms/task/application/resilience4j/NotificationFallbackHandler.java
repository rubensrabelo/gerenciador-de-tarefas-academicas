package io.github.rubensrabelo.ms.task.application.resilience4j;

import org.springframework.stereotype.Component;

@Component
public class NotificationFallbackHandler {
    public String handle(String name, String email, Throwable t) {
        return "Task '" + name + "' created, but email could not be sent to " + email +
                ".\nReason: " + t.getMessage();
    }
}
