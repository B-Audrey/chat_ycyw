import { Injectable } from '@angular/core';
import { Store } from '@ngxs/store';
import { ConnectWebSocket } from '@ngxs/websocket-plugin';

@Injectable({ providedIn: 'root' })
export class WebSocketService {
  constructor(private store: Store) {}

  connect() {
    const token = this.store.selectSnapshot((state) => state.user.accessToken);
    if (!token) {
      console.warn('No access token available, cannot connect WebSocket.');
      return;
    }

    const wsUrl = `ws://localhost:3000/ws?token=${token}`;
    this.store.dispatch(new ConnectWebSocket({ url: wsUrl }));
  }
}
