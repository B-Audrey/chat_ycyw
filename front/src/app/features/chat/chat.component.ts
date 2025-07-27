import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { Store } from '@ngxs/store';
import { ChatMessage, ChatState, MessageStatus } from '../../../shared';
import { FormsModule } from '@angular/forms';
import { AsyncPipe } from '@angular/common';
import { SendChatMessage } from '../../../shared/store/chat/chat.actions';
import { map } from 'rxjs';
import { ChatStompService } from '../../../shared/services/chat.stomp.service';

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
  private wsService = inject(ChatStompService);

  messages$ = this.store
    .select(ChatState.messages)
    .pipe(map((message) => message.content));

  newMessage = '';

  send() {
    if (!this.newMessage.trim()) {
      return;
    }
    this.wsService.sendMessage({
      toUuid: 'uuid',
      content: this.newMessage,
      status: MessageStatus.SENT,
    });
    this.newMessage = '';
  }
}
