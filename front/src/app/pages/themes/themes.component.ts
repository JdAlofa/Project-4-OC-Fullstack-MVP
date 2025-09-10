import { Component, OnInit } from '@angular/core';
import { forkJoin } from 'rxjs';
import { Theme } from 'src/app/interfaces/theme';
import { ThemeService } from 'src/app/services/theme/theme.service';

@Component({
  selector: 'app-themes',
  templateUrl: './themes.component.html',
  styleUrls: ['./themes.component.scss'],
})
export class ThemesComponent implements OnInit {
  public allThemes: Theme[] = [];
  public subscribedThemeIds: number[] = [];

  constructor(private themeService: ThemeService) {}

  ngOnInit(): void {
    //expecting results from two observables so we use rxjs forkjoin to wait for the two of them to complete
    forkJoin({
      themes: this.themeService.getThemes(),
      subscriptions: this.themeService.getSubscriptions(),
    }).subscribe(({ themes, subscriptions }) => {
      this.allThemes = themes;
      this.subscribedThemeIds = subscriptions.map((theme) => theme.id);
    });
  }

  public isSubscribed(themeId: number): boolean {
    return this.subscribedThemeIds.includes(themeId);
  }

  public subscribeToTheme(themeId: number): void {
    this.themeService.subscribe(themeId).subscribe({
      next: () => {
        this.subscribedThemeIds.push(themeId);
      },
      error: (err) => console.error('Failed to subscribe', err),
    });
  }
}
