import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Theme } from 'src/app/interfaces/theme';

@Injectable({
  providedIn: 'root',
})
export class ThemeService {
  private pathService = 'api/themes';
  constructor(private httpClient: HttpClient) {}

  public getThemes(): Observable<Theme[]> {
    return this.httpClient.get<Theme[]>(this.pathService);
  }

 public getSubscriptions(): Observable<Theme[]> {
    return this.httpClient.get<Theme[]>(`${this.pathService}/subscriptions`);
  }
  
  public subscribe(themeId: number): Observable<void> {
    return this.httpClient.post<void>(
      `${this.pathService}/${themeId}/subscribe`,
      null
    );
  }
}
