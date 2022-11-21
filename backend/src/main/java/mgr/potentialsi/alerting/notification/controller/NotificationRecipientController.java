package mgr.potentialsi.alerting.notification.controller;


import lombok.RequiredArgsConstructor;
import mgr.potentialsi.alerting.notification.NotificationRecipientService;
import mgr.potentialsi.alerting.notification.dto.NotificationRecipientDTO;
import mgr.potentialsi.alerting.notification.mapper.NotificationRecipientEntityDtoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/notifications/recipients")
@RequiredArgsConstructor
public class NotificationRecipientController {

    private final NotificationRecipientService notificationRecipientService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<NotificationRecipientDTO> getNotificationRecipients() {
        return notificationRecipientService.getNotificationRecipients().stream().map(NotificationRecipientEntityDtoMapper::toDto).toList();
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public NotificationRecipientDTO getNotificationRecipient(@PathVariable Integer id) {
        return NotificationRecipientEntityDtoMapper.toDto(notificationRecipientService.getNotificationRecipient(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addNotificationRecipient(@RequestBody NotificationRecipientDTO dto) {
        notificationRecipientService.addNotificationRecipient(NotificationRecipientEntityDtoMapper.toEntityWithoutId(dto));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNotificationRecipient(@PathVariable Integer id) {
        notificationRecipientService.deleteNotificationRecipient(id);
    }

}