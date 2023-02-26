import { NotificationChannel } from '../email-config/model/notification-channel';

export function notificationChannelSorter(
  array: NotificationChannel[]
): NotificationChannel[] {
  return array.sort((n1, n2) => {
    if (n1.type > n2.type) {
      return 1;
    }
    if (n1.type < n2.type) {
      return -1;
    }
    return 0;
  });
}
