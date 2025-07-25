import {ChangeDetectionStrategy, Component, inject} from '@angular/core';
import {FormBuilder, ReactiveFormsModule, Validators} from '@angular/forms';
import {Store} from '@ngxs/store';
import {Router} from '@angular/router';
import {ToastService, UserActions} from '../../../shared';
import {MatFormField, MatLabel} from '@angular/material/form-field';
import {MatInput} from '@angular/material/input';
import {MatButton} from '@angular/material/button';
import {HttpErrorResponse} from '@angular/common/http';

@Component({
  selector: 'chat-login',
  standalone: true,
  imports: [ReactiveFormsModule, MatInput, MatFormField, MatButton, MatLabel],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LoginComponent {
  #fb = inject(FormBuilder);
  #store = inject(Store);
  #router = inject(Router);
  #toastService = inject(ToastService);

  loginForm = this.#fb.nonNullable.group({
    username: ['' as string, [Validators.required]],
    password: ['' as string, [Validators.required, Validators.minLength(8)]],
  });

  login$({ username, password }: { username: string; password: string }) {
    if (this.loginForm.valid) {
      this.#store.dispatch(new UserActions.Login(username, password)).subscribe({
        next: () => this.#router.navigate(['/']),
        error: (e: HttpErrorResponse) => {
          if (e.status === 500) {
            return this.#toastService.error('Erreur 500, le service ne répond pas');
          }
          this.#toastService.error('Connexion refusée, veuillez vérifier vos identifiants.');
        },
      });
    }
  }
}
