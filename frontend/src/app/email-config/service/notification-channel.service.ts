import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { NotificationChannel } from '../model/notification-channel';

@Injectable()
export class NotificationChannelService {
  private configUrl = environment.apiUrl + '/notifications/channels';

  constructor(private http: HttpClient) {}

  getAllChannels(): Observable<NotificationChannel[]> {
    return this.http.get<NotificationChannel[]>(this.configUrl);
  }

  saveNotificationChannels(notificationChannels: NotificationChannel[]) {
    return this.http.post<void>(this.configUrl, notificationChannels);
  }
}
