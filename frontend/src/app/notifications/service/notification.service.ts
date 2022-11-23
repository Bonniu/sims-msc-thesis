import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Notification } from '../model/notification';
import { Observable } from 'rxjs';

@Injectable()
export class NotificationService {
  private configUrl = 'http://localhost:8080/notifications';

  constructor(private http: HttpClient) {}

  getAllNotifications(): Observable<Notification[]> {
    return this.http.get<Notification[]>(this.configUrl);
  }

  updateNotification(notification: Notification): Observable<void> {
    return this.http.put<void>(
      this.configUrl + '/' + notification.id,
      notification
    );
  }
}
