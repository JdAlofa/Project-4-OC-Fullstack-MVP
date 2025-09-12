import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse,
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { SessionService } from '../session/session.service';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  constructor(private sessionService: SessionService) {}

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    // Do not attach token for login or register routes
    if (
      request.url.includes('/api/auth/login') ||
      request.url.includes('/api/auth/register')
    ) {
      return next.handle(request);
    }

    const token = localStorage.getItem('token');

    if (token) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`,
        },
      });
    }

    // Using pipe() to add error handling to the request
    return next.handle(request).pipe(
      catchError((error: HttpErrorResponse) => {
        // If we get a 401 Unauthorized, the token is bad. Log the user out.
        if (error.status === 401) {
          this.sessionService.logOut();
        }
        // Re-throw the error to be caught by the calling service
        return throwError(() => error);
      })
    );
  }
}
