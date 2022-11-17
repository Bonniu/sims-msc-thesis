import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {NotificationService} from "./service/notification.service";
import {Notification} from "./model/notification";

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.scss']
})
export class NotificationsComponent implements OnInit {

  notifications: Notification[] = [];
  displayedColumns: string[] = ['message'];

  constructor(
    private http: HttpClient,
    private notificationService: NotificationService
  ) {
  }

  ngOnInit() {
    console.log("notificationComponent start")
    this.notificationService.getAllNotifications().subscribe(
      next => this.notifications = next);
    console.log(this.notifications)
  }

}
