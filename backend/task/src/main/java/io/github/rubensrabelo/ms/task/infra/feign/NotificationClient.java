package io.github.rubensrabelo.ms.task.infra.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "msnotification", path = "/v1/notifications")
public interface NotificationClient {

    @GetMapping("/send")
    public ResponseEntity<String> send(
            @RequestParam String message,
            @RequestParam String to
    );
}
