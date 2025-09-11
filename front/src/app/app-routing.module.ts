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

// consider a guard combined with canLoad / canActivate route option
// to manage unauthenticated user to access private routes
const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'themes', component: ThemesComponent },
  { path: 'articles', component: ArticlesComponent },
  { path: 'articles/create', component: CreateArticleComponent },
  { path: 'articles/:id', component: ArticleDetailComponent },
  { path: 'me', component: UserComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
