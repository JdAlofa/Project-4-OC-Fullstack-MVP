import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RegisterRequest } from 'src/app/interfaces/registerRequest';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private pathService = 'api/auth';
  constructor(private httpClient: HttpClient) {
  }

  public register(registerRequest: RegisterRequest): Observable<any> {
    return this.httpClient.post('api/auth/register', registerRequest);
  }
}
