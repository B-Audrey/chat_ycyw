/* eslint-disable no-unused-vars */

import { ChatMessage } from '../../interfaces';
import { PageQueryParams, SortDirection } from '../../interfaces';

export class OnMessageReceived {
  static readonly type = '[Chat] Act on Message';

  constructor(public payload: ChatMessage) {}
}

export class SendChatMessage {
  static readonly type = '[Chat] Send Message';

  constructor(
    public type: string,
    public payload: ChatMessage,
  ) {}
}

export class LoadChatHistory {
  static readonly type = '[Chat] Load History';

  constructor(
    public params: PageQueryParams = {
      page: 0,
      size: 20,
      sort: SortDirection.DESC,
    },
  ) {}
}
