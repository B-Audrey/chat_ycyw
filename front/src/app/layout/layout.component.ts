import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatMenuModule } from '@angular/material/menu';
import { Store } from '@ngxs/store';
import { UserActions, UserState } from '../../shared';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import {
  MatSidenav,
  MatSidenavContainer,
  MatSidenavContent,
} from '@angular/material/sidenav';
import { ChatComponent } from '../features/chat/chat.component';
import { WebSocketService } from '../../shared';

@Component({
  selector: 'chat-layout',
  standalone: true,
  imports: [
    CommonModule,
    RouterOutlet,
    MatButtonModule,
    MatMenuModule,
    MatToolbarModule,
    MatIconModule,
    MatSidenav,
    ChatComponent,
    MatSidenavContent,
    MatSidenavContainer,
  ],
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export default class LayoutComponent {
  private readonly store = inject(Store);
  private wsService = inject(WebSocketService);

  readonly user$ = this.store.select(UserState.getMe);

  logout() {
    this.store.dispatch(new UserActions.Logout());
  }

  connectWS() {
    // this.store.dispatch(new LoadChatHistory({
    //   page: 0,
    //   size: 20,
    //   sort: SortDirection.DESC
    // }));
    this.wsService.connect();
  }
}
