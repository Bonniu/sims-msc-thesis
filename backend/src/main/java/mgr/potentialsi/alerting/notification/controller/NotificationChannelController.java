package mgr.potentialsi.alerting.notification.controller;


import lombok.RequiredArgsConstructor;
import mgr.potentialsi.alerting.notification.NotificationChannelService;
import mgr.potentialsi.alerting.notification.dto.NotificationChannelDTO;
import mgr.potentialsi.alerting.notification.mapper.NotificationChannelEntityDtoMapper;
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

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public NotificationChannelDTO getNotificationChannel(@PathVariable Integer id) {
        return NotificationChannelEntityDtoMapper.toDto(notificationChannelService.getNotificationChannel(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addNotificationChannel(@RequestBody NotificationChannelDTO dto) {
        notificationChannelService.addNotificationChannel(NotificationChannelEntityDtoMapper.toEntity(dto));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNotificationChannel(@PathVariable Integer id) {
        notificationChannelService.deleteNotificationChannel(id);
    }


}