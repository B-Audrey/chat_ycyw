import {ChangeDetectionStrategy, Component, inject} from '@angular/core';
import {Store} from '@ngxs/store';
import {ChatState, MessageStatus} from '../../../shared';
import {FormsModule} from '@angular/forms';
import {AsyncPipe} from '@angular/common';
import {map} from 'rxjs';
import {ChatStompService} from '../../../shared/services/chat.stomp.service';
import {MatIconButton} from '@angular/material/button';
import {MatIcon} from '@angular/material/icon';
import {MatInput} from '@angular/material/input';
import {MatFormField} from '@angular/material/form-field';

@Component({
  selector: 'chat-content',
  standalone: true,
  imports: [FormsModule, AsyncPipe, MatIconButton, MatIcon, MatFormField, MatInput],
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
      body: this.newMessage,
      status: MessageStatus.SENT,
    });
    this.newMessage = '';
  }
}
