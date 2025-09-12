import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { SessionService } from '../../session/session.service';

@Injectable({
  providedIn: 'root',
})
export class UnauthGuard implements CanActivate {
  constructor(private sessionService: SessionService, private router: Router) {}

  canActivate(): boolean {
    // Check the current login status
    if (this.sessionService.isLogged.value) {
      // If logged in, redirect to the main articles page
      this.router.navigate(['/articles']);
      return false; // Block navigation to login/register
    }
    // If not logged in, allow navigation
    return true;
  }
}
