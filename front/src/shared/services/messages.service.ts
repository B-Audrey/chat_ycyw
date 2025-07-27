import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ChatMessage, Page, PageQueryParams } from '../interfaces';

@Injectable({ providedIn: 'root' })
export class MessagesService {
  private readonly http = inject(HttpClient);

  loadHistory$(params: PageQueryParams) {
    return this.http.get<Page<ChatMessage>>('/api/messages/history', {
      params: { ...params },
    });
  }
}
