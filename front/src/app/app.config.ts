import {ApplicationConfig, importProvidersFrom, LOCALE_ID, provideZoneChangeDetection,} from '@angular/core';
import {provideRouter, withComponentInputBinding} from '@angular/router';
import {provideStore} from '@ngxs/store';
import {provideAnimationsAsync} from '@angular/platform-browser/animations/async';
import {provideHttpClient, withFetch, withInterceptors} from '@angular/common/http';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MAT_FORM_FIELD_DEFAULT_OPTIONS} from '@angular/material/form-field';
import {NgxsReduxDevtoolsPluginModule} from '@ngxs/devtools-plugin';
import {routes} from './app.routes';
import {ChatState, UserState} from '../shared';
import {authInterceptor} from './auth-interceptor';
import {NgxsWebSocketPluginModule } from '@ngxs/websocket-plugin';


export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes, withComponentInputBinding()),
    provideStore([UserState, ChatState]),
    importProvidersFrom(
      BrowserAnimationsModule,
      NgxsReduxDevtoolsPluginModule.forRoot({ disabled: false }),
      NgxsWebSocketPluginModule.forRoot({
        url: 'http://localhost:8080/ws'
      })
    ),
    { provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: { appearance: 'outline' } },
    provideAnimationsAsync(),
    provideHttpClient(withInterceptors([authInterceptor]), withFetch()),
    { provide: LOCALE_ID, useValue: navigator.language }, // Utilisation de la langue du navigateur
  ],
};
