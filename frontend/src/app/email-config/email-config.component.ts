import { Component, OnInit } from '@angular/core';
import { NotificationRecipientService } from './service/notification-recipient.service';
import { NotificationRecipient } from './model/notification-recipient';

@Component({
  selector: 'email-config',
  templateUrl: './email-config.component.html',
  styleUrls: ['./email-config.component.scss'],
})
export class EmailConfigComponent implements OnInit {
  notificationRecipients: NotificationRecipient[] = [];
  newRecipientEmail: string = '';

  constructor(
    private notificationRecipientService: NotificationRecipientService
  ) {}

  ngOnInit() {
    this.notificationRecipientService
      .getAllNotificationRecipients()
      .subscribe((next) => (this.notificationRecipients = next));
  }

  saveNotificationChannels() {
    console.log('saveNotificationChannels');
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
