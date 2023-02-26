import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { NotificationService } from './service/notification.service';
import { Notification } from './model/notification';
import { MatTable } from '@angular/material/table';
import { MessageModalComponent } from './message-modal/message-modal.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.scss'],
})
export class NotificationsComponent implements OnInit {
  notifications: Notification[] = [];
  displayedColumns: string[] = [
    'id',
    'timestamp',
    'messageType',
    'message',
    'seen',
    'selected',
  ];
  @ViewChild(MatTable) notificationsTable!: MatTable<any>;

  constructor(
    private http: HttpClient,
    private notificationService: NotificationService,
    private dialog: MatDialog
  ) {}

  ngOnInit() {
    this.notificationService
      .getAllNotifications()
      .subscribe((next) => (this.notifications = next));
  }

  getClassForNotification(notification: Notification) {
    let notificationClass = '';
    if (!notification.seen) {
      notificationClass += 'fw-bold ';
    }
    if (notification.messageType == 'ERROR')
      notificationClass += 'text-danger ';
    if (notification.messageType == 'WARNING')
      notificationClass += 'text-dark ';
    if (notification.messageType == 'FATAL')
      notificationClass += 'text-danger ';
    return notificationClass;
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

  showMessage(notification: Notification) {
    const dialogRef = this.dialog.open(MessageModalComponent, {
      data: notification,
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        // do something
      }
    });
  }

  deleteSelected(notifications: Notification[]) {
    let ids = notifications.filter((n) => n.selected).map((n) => n.id);
    for (const id of ids) {
      this.notificationService.deleteNotification(id).subscribe((next) => {});
    }
    window.location.reload();
  }

  deleteAll() {
    for (const id of this.notifications.map((n) => n.id)) {
      this.notificationService.deleteNotification(id).subscribe((next) => {});
    }
    window.location.reload();
  }
}
