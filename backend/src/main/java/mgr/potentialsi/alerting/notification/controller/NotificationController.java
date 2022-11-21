package mgr.potentialsi.alerting.notification.controller;

import lombok.RequiredArgsConstructor;
import mgr.potentialsi.alerting.notification.NotificationService;
import mgr.potentialsi.alerting.notification.dto.NotificationDTO;
import mgr.potentialsi.alerting.notification.mapper.NotificationEntityDtoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<NotificationDTO> getNotifications() {
        return notificationService.getNotifications().stream().map(NotificationEntityDtoMapper::toDto).toList();
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public NotificationDTO getNotifications(@PathVariable Integer id) {
        return NotificationEntityDtoMapper.toDto(notificationService.getNotification(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addNotification(@RequestBody NotificationDTO dto) {
        notificationService.addNotification(NotificationEntityDtoMapper.toEntity(dto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNotification(@PathVariable Integer id) {
        notificationService.deleteNotification(id);
    }


    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifyNotificationRecipient(@PathVariable Integer id, @RequestBody NotificationDTO dto) {
        notificationService.modifyNotification(id, NotificationEntityDtoMapper.toEntity(dto));
    }

}
