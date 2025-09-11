import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Article } from 'src/app/interfaces/article';
import { ArticleService } from 'src/app/services/articles/article.service';

@Component({
  selector: 'app-article-detail',
  templateUrl: './article-detail.component.html',
  styleUrls: ['./article-detail.component.scss'],
})
export class ArticleDetailComponent implements OnInit {
  public article: Article | undefined;
  public commentForm: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private articleService: ArticleService,
    private fb: FormBuilder
  ) {
    this.commentForm = this.fb.group({
      content: ['', [Validators.required, Validators.maxLength(2000)]],
    });
  }

  ngOnInit(): void {
    this.fetchArticle();
  }

  private fetchArticle(): void {
    const articleId = this.route.snapshot.paramMap.get('id');
    if (articleId) {
      this.articleService.getArticle(articleId).subscribe((article) => {
        this.article = article;
      });
    }
  }

  public submitComment(): void {
    if (this.commentForm.valid && this.article) {
      const content = this.commentForm.value.content;
      this.articleService
        .postComment(String(this.article.id), content)
        .subscribe({
          next: () => {
            this.commentForm.reset();
            this.fetchArticle();
          },
          error: (err) => console.error('Failed to post comment', err),
        });
    }
  }

  public goBack(): void {
    this.router.navigate(['/articles']);
  }
}
