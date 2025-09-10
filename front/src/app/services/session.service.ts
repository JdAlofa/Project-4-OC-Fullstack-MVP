import { Injectable } from '@angular/core';
import { Router } from '@angular/router'; 
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  public isLogged = new BehaviorSubject<boolean>(this.hasToken());

  constructor(private router: Router) { } 

  private hasToken(): boolean {
    return !!localStorage.getItem('token');
  }

  public logIn(token: string): void {
    localStorage.setItem('token', token);
    this.isLogged.next(true);
  }

  public logOut(): void {
    localStorage.removeItem('token');
    this.isLogged.next(false);
    this.router.navigate(['/login']);
  }
}