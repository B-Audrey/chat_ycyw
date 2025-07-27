import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { Store } from '@ngxs/store';
import { ChatMessage, ChatState, MessageStatus } from '../../../shared';
import { FormsModule } from '@angular/forms';
import { AsyncPipe } from '@angular/common';
import { SendChatMessage } from '../../../shared/store/chat/chat.actions';
import { map } from 'rxjs';

@Component({
  selector: 'chat-content',
  standalone: true,
  imports: [FormsModule, AsyncPipe],
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ChatComponent {
  private store = inject(Store);

  messages$ = this.store
    .select(ChatState.messages)
    .pipe(map((message) => message.content));
  newMessage = '';

  send() {
    const message: ChatMessage = {
      toUuid: 'uuid',
      content: this.newMessage,
      status: MessageStatus.SENT,
    };
    console.log('Sending message:', message);
    this.newMessage = '';
  }
}
