import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/auth/login/login.component';
import { RegisterComponent } from './pages/auth/register/register.component';
import { ThemesComponent } from './pages/themes/themes.component';
import { ArticlesComponent } from './pages/articles/articles.component';
import { UserComponent } from './pages/user/user.component';
import { CreateArticleComponent } from './pages/articles/create-article/create-article.component';
import { ArticleDetailComponent } from './pages/articles/article-detail/article-detail.component';
// --- Import the guards ---
import { AuthGuard } from './services/auth/guards/auth.guard';
import { UnauthGuard } from './services/auth/guards/unauth.guard';

const routes: Routes = [
  // --- Apply UnauthGuard to public-only routes ---
  { path: '', component: HomeComponent, canActivate:[UnauthGuard] },
  { path: 'login', component: LoginComponent, canActivate: [UnauthGuard] },
  { path: 'register', component: RegisterComponent, canActivate: [UnauthGuard] },
  
  // --- Apply AuthGuard to protected routes ---
  { path: 'themes', component: ThemesComponent, canActivate: [AuthGuard] },
  { path: 'articles', component: ArticlesComponent, canActivate: [AuthGuard] },
  { path: 'articles/create', component: CreateArticleComponent, canActivate: [AuthGuard] },
  { path: 'articles/:id', component: ArticleDetailComponent, canActivate: [AuthGuard] },
  { path: 'me', component: UserComponent, canActivate: [AuthGuard] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
