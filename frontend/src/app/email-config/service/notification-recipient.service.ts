import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NotificationRecipient } from '../model/notification-recipient';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable()
export class NotificationRecipientService {
  private configUrl = environment.apiUrl + '/notifications/recipients';

  constructor(private http: HttpClient) {}

  getAllNotificationRecipients(): Observable<NotificationRecipient[]> {
    return this.http.get<NotificationRecipient[]>(this.configUrl);
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
}
