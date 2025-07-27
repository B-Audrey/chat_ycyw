import { Injectable, OnDestroy } from '@angular/core';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { Store } from '@ngxs/store';
import { UserState } from '../store/user';
import { OnMessageReceived } from '../store/chat/chat.actions';
import { ChatMessage } from '../interfaces';

@Injectable({ providedIn: 'root' })
export class ChatStompService implements OnDestroy {
  private stomp?: Client;
  private subId?: string;

  constructor(private store: Store) {}

  connect(token?: string) {
    if (this.stomp?.connected) {
      return;
    }

    const url = '/api/ws';
    const jwt = token ?? this.store.selectSnapshot(UserState.getAccessToken);

    this.stomp = new Client({
      webSocketFactory: () =>
        new SockJS(`${url}?token=${jwt}`, undefined, {
          transports: ['websocket', 'xhr-streaming'],
        }),
      beforeConnect: () => {
        const fresh = this.store.selectSnapshot(UserState.getAccessToken);
        this.stomp!.connectHeaders = { Authorization: `Bearer ${fresh}` };
      },
      reconnectDelay: 5000,
      debug: (str) => console.log(str),
      onConnect: () => {
        this.subId = this.stomp!.subscribe('/collaborator/messages', (msg) =>
          this.store.dispatch(new OnMessageReceived(JSON.parse(msg.body))),
        ).id;
      },
      onStompError: (frame) => console.error('STOMP error', frame),
      onDisconnect: (_) => console.warn('WS déconnecté – reconnexion dans 5 s'),
    });

    this.stomp.activate();
  }

  sendMessage(payload: ChatMessage) {
    if (!this.stomp?.connected) {
      return;
    }
    this.stomp.publish({
      destination: '/app/chat.sendMessage',
      body: JSON.stringify(payload),
    });
  }

  disconnect() {
    if (this.subId) {
      this.stomp?.unsubscribe(this.subId);
    }
    this.stomp?.deactivate();
  }

  ngOnDestroy() {
    this.disconnect();
  }
}
