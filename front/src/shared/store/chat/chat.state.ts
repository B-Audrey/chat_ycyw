import { Action, Selector, State, StateContext } from '@ngxs/store';
import { ConnectWebSocket, SendWebSocketMessage } from '@ngxs/websocket-plugin';
import { ChatMessage, Page } from '../../interfaces';
import { inject, Injectable } from '@angular/core';
import * as ChatActions from './chat.actions';
import { MessagesService } from '../../services/messages.service';
import { tap } from 'rxjs/operators';

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
    console.log('Message a envoyer message dans le store:', event);
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
    console.log('Received message dans le store:', event);
    console.log(
      'Adding message to state for reading to customer:',
      event.payload,
    );
    const messages = ctx.getState();
    ctx.setState({
      content: [...messages.content, event.payload],
      pagination: {
        ...messages.pagination,
        total: messages.pagination.total + 1,
      },
    });
  }

  @Action(ChatActions.LoadChatHistory)
  loadHistory(
    ctx: StateContext<ChatStateModel>,
    action: ChatActions.LoadChatHistory,
  ) {
    return this.messagesService.loadHistory$(action.params).pipe(
      tap((page: Page<ChatMessage>) => {
        ctx.setState({
          content: page.content,
          pagination: {
            total: page.pagination.total,
            page: page.pagination.page,
            size: page.pagination.size,
          },
        });
      }),
    );
  }
}
