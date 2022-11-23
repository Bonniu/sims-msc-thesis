import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { NotificationService } from './service/notification.service';
import { Notification } from './model/notification';
import { MatTable } from '@angular/material/table';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.scss'],
})
export class NotificationsComponent implements OnInit {
  notifications: Notification[] = [];
  displayedColumns: string[] = [
    'id',
    'message',
    'errorType',
    'timestamp',
    'seen',
  ];
  @ViewChild(MatTable) notificationsTable!: MatTable<any>;

  constructor(
    private http: HttpClient,
    private notificationService: NotificationService
  ) {}

  ngOnInit() {
    this.notificationService
      .getAllNotifications()
      .subscribe((next) => (this.notifications = next));
  }

  getClassForNotification(notification: Notification) {
    return !notification.seen ? 'fw-bold' : '';
  }

  updateNotification(notification: Notification) {
    this.notificationService.updateNotification(notification).subscribe();
  }

  // FIXME update idzie pojedynczo
  markAllAsSeen() {
    this.notifications.forEach((n) => {
      n.seen = true;
      this.updateNotification(n);
    });
  }
}
