export interface Notification {
  id: number;
  message: string;
  timestamp: number;
  messageType: string;
  seen: boolean;
  selected: boolean;
}
