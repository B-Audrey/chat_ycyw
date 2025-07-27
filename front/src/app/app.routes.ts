import { Route } from '@angular/router';
import { authGuard } from './auth.guard';

import LayoutComponent from './layout/layout.component';
import { LoginComponent } from './features/login/login.component';
import { ErrorComponent } from './features/error/error.component';
import { ChatComponent } from './features/chat/chat.component';
import { HomeComponent } from './features/app/home.component';

export const routes: Route[] = [
  {
    path: '',
    component: LayoutComponent,
    canActivate: [authGuard],
    children: [
      {
        path: '',
        pathMatch: 'full',
        redirectTo: 'home',
      },
      {
        path: 'home',
        component: HomeComponent,
      },
    ],
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'errors',
    component: ErrorComponent,
  },
  {
    path: '**',
    component: ErrorComponent,
  },
];
