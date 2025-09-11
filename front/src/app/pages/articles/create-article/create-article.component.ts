import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { Theme } from 'src/app/interfaces/theme';
import { ArticleService } from 'src/app/services/articles/article.service';
import { ThemeService } from 'src/app/services/theme/theme.service';
@Component({
  selector: 'app-create-article',
  templateUrl: './create-article.component.html',
  styleUrls: ['./create-article.component.scss'],
})
export class CreateArticleComponent implements OnInit {
  public articleForm: FormGroup;
  public themes$: Observable<Theme[]> = of([]);

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private articleService: ArticleService,
    private themeService: ThemeService
  ) {
    this.articleForm = this.fb.group({
      themeId: ['', [Validators.required]],
      title: ['', [Validators.required, Validators.maxLength(50)]],
      content: ['', [Validators.required, Validators.maxLength(2000)]],
    });
  }

  ngOnInit(): void {
    this.themes$ = this.themeService.getThemes();
  }

  public submit(): void {
    if (this.articleForm.valid) {
      this.articleService.createArticle(this.articleForm.value).subscribe(
        (createdArticle) => {
          // On success, navigate to the new article's detail page
          this.router.navigate(['/articles', createdArticle.id]);
        },
        (error) => console.error('Failed to create article', error)
      );
    }
  }

  public goBack(): void {
    window.history.back();
  }
}
