import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Notification } from '../model/notification';
import { Observable } from 'rxjs';

@Injectable()
export class NotificationService {
  private configUrl = 'http://localhost:8080/notifications';

  constructor(private http: HttpClient) {}

  getAllNotifications(): Observable<Notification[]> {
    let observable = this.http.get<Notification[]>(this.configUrl);
    console.log(observable)
    return observable;
  }

  updateNotification(notification: Notification): Observable<void> {
    return this.http.put<void>(
      this.configUrl + '/' + notification.id,
      notification
    );
  }

  deleteNotification(notificationId: number): Observable<void> {
    return this.http.delete<void>(
      this.configUrl + '/' + notificationId,
    );
  }

}
