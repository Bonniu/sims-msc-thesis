import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NotificationRecipient } from '../model/notification-recipient';
import { Observable } from 'rxjs';

@Injectable()
export class NotificationRecipientService {
  private configUrl = 'http://localhost:8080/notifications/recipients';

  constructor(private http: HttpClient) {}

  getAllNotificationRecipients(): Observable<NotificationRecipient[]> {
    let observable = this.http.get<NotificationRecipient[]>(this.configUrl);
    console.log(observable);
    return observable;
  }

  deleteNotificationRecipient(
    notificationRecipientId: number
  ): Observable<void> {
    return this.http.delete<void>(
      this.configUrl + '/' + notificationRecipientId
    );
  }

  addNewNotificationRecipient(recipientEmail: string): Observable<void> {
    return this.http.post<void>(this.configUrl, {
      email: recipientEmail,
    });
  }

  saveNotificationRecipients() {
    // return this.http.delete<void>(
    //   this.configUrl + '/' + notificationRecipientId
    // );
  }
}
