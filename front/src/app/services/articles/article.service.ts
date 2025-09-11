import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Article } from 'src/app/interfaces/article';

@Injectable({
  providedIn: 'root',
})
export class ArticleService {
  private pathService = 'api/articles';

  constructor(private httpClient:HttpClient) {}

  public getFeed(): Observable<Article[]> {
    return this.httpClient.get<Article[]>(this.pathService);
  }

   public getArticle(id: string): Observable<Article> {
    return this.httpClient.get<Article>(`${this.pathService}/${id}`);
  }

  public postComment(articleId: string, content: string): Observable<Comment> {
    const body = { content };
    return this.httpClient.post<Comment>(`${this.pathService}/${articleId}/comments`, body);
  }
}
