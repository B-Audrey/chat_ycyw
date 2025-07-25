import {User} from './user';

export interface ChatMessage {
  uuid?: string;
  from?: User;
  to?: User;
  content: string;
  status: MessageStatus;
  createdAt?: string;
  updatedAt?: string;
  deletedAt?: string;
}

export enum MessageStatus {
  SENT = 'SENT',
  READ = 'READ',
  ARCHIVED = 'ARCHIVED',
}
