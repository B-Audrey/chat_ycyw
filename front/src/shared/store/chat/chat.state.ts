import {Action, Selector, State, StateContext} from '@ngxs/store';
import {ConnectWebSocket, SendWebSocketMessage} from '@ngxs/websocket-plugin';
import {ChatMessage, Page} from '../../interfaces';
import {inject, Injectable} from '@angular/core';
import * as ChatActions from './chat.actions';
import {MessagesService} from '../../services/messages.service';

export interface ChatStateModel {
  content: ChatMessage[];
  pagination: {
    total: number;
    page: number;
    size: number;
  };
}

@State<Page<ChatMessage>>({
  name: 'chat',
  defaults: {
    content: [],
    pagination: {
      total: 0,
      page: 0,
      size: 10,
    },
  },
})
@Injectable()
export class ChatState {
  readonly messagesService = inject(MessagesService);

  @Selector()
  static messages(state: ChatStateModel) {
    return state;
  }

  @Action(ConnectWebSocket)
  connect(ctx: StateContext<ChatStateModel>) {
    // Abonnement auto
  }

  @Action(ChatActions.SendChatMessage)
  sendMessage(
    ctx: StateContext<ChatStateModel>,
    event: ChatActions.SendChatMessage,
  ) {
    ctx.dispatch(
      new SendWebSocketMessage({
        type: '/app/chat',
        payload: event.payload,
      }),
    );
  }

  @Action(ChatActions.OnMessageReceived)
  onMessage(
    ctx: StateContext<ChatStateModel>,
    event: ChatActions.OnMessageReceived,
  ) {
    const messages = ctx.getState();
    ctx.setState({
      content: [...messages.content, event.payload],
      pagination: {
        ...messages.pagination,
        total: messages.pagination.total + 1,
      },
    });

  }
}
