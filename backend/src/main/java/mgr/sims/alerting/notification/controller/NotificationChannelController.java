package mgr.sims.alerting.notification.controller;


import lombok.RequiredArgsConstructor;
import mgr.sims.alerting.notification.NotificationChannelService;
import mgr.sims.alerting.notification.dto.NotificationChannelDTO;
import mgr.sims.alerting.notification.mapper.NotificationChannelEntityDtoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/notifications/channels")
@RequiredArgsConstructor
public class NotificationChannelController {

    private final NotificationChannelService notificationChannelService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<NotificationChannelDTO> getNotificationChannels() {
        return notificationChannelService.getNotificationChannels().stream().map(NotificationChannelEntityDtoMapper::toDto).toList();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNotificationChannel(@PathVariable Integer id) {
        notificationChannelService.deleteNotificationChannel(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void saveNotificationChannels(@RequestBody List<NotificationChannelDTO> list) {
        notificationChannelService.saveNotificationChannels(list);
    }


}