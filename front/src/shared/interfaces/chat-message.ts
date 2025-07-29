export interface ChatMessage {
  uuid?: string;
  fromUuid?: string;
  fromLastName?: string;
  fromFirstName?: string;
  body: string;
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
