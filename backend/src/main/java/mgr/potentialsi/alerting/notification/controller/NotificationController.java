package mgr.potentialsi.alerting.notification.controller;

import lombok.RequiredArgsConstructor;
import mgr.potentialsi.alerting.notification.NotificationService;
import mgr.potentialsi.alerting.notification.dto.NotificationDTO;
import mgr.potentialsi.alerting.notification.mapper.NotificationEntityDtoMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public List<NotificationDTO> getNotifications() {
        return notificationService.getNotifications().stream().map(NotificationEntityDtoMapper::toDto).toList();
    }
}
