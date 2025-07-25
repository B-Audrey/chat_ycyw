import {ChangeDetectionStrategy, Component, inject, OnInit} from '@angular/core';
import {Store} from '@ngxs/store';
import {ChatMessage, ChatState, MessageStatus, Role, SortDirection, User, UserState} from '../../../shared';
import {FormsModule} from '@angular/forms';
import {AsyncPipe} from '@angular/common';
import {LoadChatHistory, SendChatMessage} from '../../../shared/store/chat/chat.actions';
import {map} from 'rxjs';

@Component({
  selector: 'chat-content',
  standalone: true,
  imports: [
    FormsModule,
    AsyncPipe
  ],
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ChatComponent implements OnInit {
  private store = inject(Store)

  private toUser: User = {
    uuid: '5e67cd39-868f-477b-99fc-b99576130189',
    email: 'user@dev.fr',
    firstName: 'User',
    lastName: 'Client',
    roles: [
      Role.ROLE_COLLABORATOR,
    ],
  }

  messages$ = this.store.select(ChatState.messages).pipe(
    map(message => message.content)
  );
  newMessage = '';

  ngOnInit(): void {
    // this.store.dispatch(new ConnectWebSocket());
    this.store.dispatch(new LoadChatHistory({
      page: 0,
      size: 20,
      sort: SortDirection.DESC
    }));
  }

  send() {
    const message: ChatMessage = {
      from: this.store.selectSnapshot(UserState.getMe),
      to: this.toUser,
      content: this.newMessage,
      status: MessageStatus.SENT,
    };
    this.store.dispatch(new SendChatMessage('/app/chat', message));
    this.newMessage = '';
  }
}
