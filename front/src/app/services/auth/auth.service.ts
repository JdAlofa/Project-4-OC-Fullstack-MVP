import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginRequest } from 'src/app/interfaces/authInterfaces/loginRequest';
import { LoginResponse } from 'src/app/interfaces/authInterfaces/LoginResponse';
import { RegisterRequest } from 'src/app/interfaces/authInterfaces/registerRequest';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private httpClient: HttpClient) {}

  public register(registerRequest: RegisterRequest): Observable<any> {
    return this.httpClient.post('api/auth/register', registerRequest);
  }

  public login(loginRequest: LoginRequest): Observable<LoginResponse> {
    return this.httpClient.post<LoginResponse>('api/auth/login', loginRequest);
  }
}
