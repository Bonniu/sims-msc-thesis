import { Component, OnInit } from '@angular/core';
import { NotificationRecipientService } from './service/notification-recipient.service';
import { NotificationRecipient } from './model/notification-recipient';
import { NotificationChannelService } from './service/notification-channel.service';
import { NotificationChannel } from './model/notification-channel';
import { notificationChannelSorter } from '../utils/notification-channel-sorter';

@Component({
  selector: 'email-config',
  templateUrl: './email-config.component.html',
  styleUrls: ['./email-config.component.scss'],
})
export class EmailConfigComponent implements OnInit {
  notificationRecipients: NotificationRecipient[] = [];
  notificationChannels: NotificationChannel[] = [];
  newRecipientEmail: string = '';

  constructor(
    private notificationRecipientService: NotificationRecipientService,
    private notificationChannelService: NotificationChannelService
  ) {}

  ngOnInit() {
    this.notificationRecipientService
      .getAllNotificationRecipients()
      .subscribe((next) => (this.notificationRecipients = next));

    this.notificationChannelService.getAllChannels().subscribe((next) => {
      this.notificationChannels = notificationChannelSorter(next);
    });
  }

  saveNotificationChannels() {
    this.notificationChannelService
      .saveNotificationChannels(this.notificationChannels)
      .subscribe((next) => {
        window.location.reload();
      });
  }

  saveNotificationRecipients(recipientEmail: string) {
    this.notificationRecipientService
      .addNewNotificationRecipient(recipientEmail)
      .subscribe((next) => {
        window.location.reload();
      });
  }

  removeRecipient(id: any) {
    this.notificationRecipientService
      .deleteNotificationRecipient(id)
      .subscribe((next) => {
        window.location.reload();
      });
  }

  fetchNotificationRecipients() {
    return this.notificationRecipientService.getAllNotificationRecipients();
  }
}
