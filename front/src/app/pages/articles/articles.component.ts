import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators'; // <-- Import the map operator
import { Article } from 'src/app/interfaces/article';
import { ArticleService } from 'src/app/services/articles/article.service';

@Component({
  selector: 'app-articles',
  templateUrl: './articles.component.html',
  styleUrls: ['./articles.component.scss'],
})
export class ArticlesComponent implements OnInit {
  public articles$: Observable<Article[]> = of([]);
  public sortOrder: 'desc' | 'asc' = 'desc';

  constructor(private articleService: ArticleService, private router: Router) {}

  ngOnInit(): void {
    this.fetchArticles();
  }

  private fetchArticles(): void {
    this.articles$ = this.articleService.getFeed().pipe(
      map((articles) => {
        // Sort the articles array before it is returned
        return articles.sort((a, b) => {
          const dateA = new Date(a.createdAt).getTime();
          const dateB = new Date(b.createdAt).getTime();
          // Sort based on the current sortOrder
          return this.sortOrder === 'desc' ? dateB - dateA : dateA - dateB;
        });
      })
    );
  }

  public changeSortOrder(order: 'desc' | 'asc'): void {
    this.sortOrder = order;
    this.fetchArticles();
  }

  public createArticle(): void {
    this.router.navigate(['/articles/create']);
  }

  public viewArticle(articleId: number): void {
    this.router.navigate(['/articles', articleId]);
  }
}
