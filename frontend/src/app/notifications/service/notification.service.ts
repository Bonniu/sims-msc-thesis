import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Notification } from '../model/notification';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable()
export class NotificationService {
  private configUrl = environment.apiUrl + '/notifications';

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

  deleteNotification(notificationId: number): Observable<void> {
    return this.http.delete<void>(this.configUrl + '/' + notificationId);
  }
}
