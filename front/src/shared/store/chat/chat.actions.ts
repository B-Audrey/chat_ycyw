/* eslint-disable no-unused-vars */

import {ChatMessage} from '../../interfaces/chatMessage';
import {Page, PageQueryParams, SortDirection} from '../../interfaces';

export class OnMessage {
  static readonly type = '[Chat] Act on Message';

  constructor(
    public type: string,
    public payload: ChatMessage
  ) {
  }
}

export class SendChatMessage {
  static readonly type = '[Chat] Send Message';

  constructor(
    public type: string,
    public payload: ChatMessage
  ) {
  }
}

export class LoadChatHistory {
  static readonly type = '[Chat] Load History';
  constructor(public params: PageQueryParams = { page: 0, size: 20, sort: SortDirection.DESC }) {}
}
