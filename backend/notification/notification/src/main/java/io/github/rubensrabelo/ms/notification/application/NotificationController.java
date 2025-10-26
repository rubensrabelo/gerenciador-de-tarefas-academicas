package io.github.rubensrabelo.ms.notification.application;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final EmailService service;

    public NotificationController(EmailService service) {
        this.service = service;
    }

    @GetMapping("/send")
    public ResponseEntity<String> send(
            @RequestParam String message,
            @RequestParam String to
    ) {
        service.send(to, "New Task Created", message);
        return ResponseEntity.ok("New Task Created");
    }
}
