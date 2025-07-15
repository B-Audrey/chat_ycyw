import {ChangeDetectionStrategy, Component} from '@angular/core';

@Component({
  selector: 'chat-content',
  standalone: true,
  imports: [],
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ChatComponent {}
