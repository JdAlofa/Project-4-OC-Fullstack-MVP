import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from 'src/app/interfaces/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private pathService = 'api/user';

  constructor(private httpClient: HttpClient) { }

  public get(): Observable<User> {
    return this.httpClient.get<User>(`${this.pathService}/me`);
  }

  public update(user: Partial<User>): Observable<void> {
    return this.httpClient.put<void>(`${this.pathService}/me`, user);
  }

  public unsubscribe(themeId: number): Observable<void> {
    return this.httpClient.delete<void>(`${this.pathService}/me/subscriptions/${themeId}`);
  }
}