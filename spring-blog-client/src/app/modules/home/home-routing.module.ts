import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RoleGuard } from 'src/app/core/guards/role-guard';
import { RoleNames } from 'src/app/core/interface/core.interface';
import { CategoriesComponent } from './pages/categories/categories.component';
import { PostDetailComponent } from './pages/post-detail/post-detail.component';
import { PostsByCategoryComponent } from './pages/posts-by-category/posts-by-category.component';
import { PostsByTagComponent } from './pages/posts-by-tag/posts-by-tag.component';
import { PostsComponent } from './pages/posts/posts.component';
import { SubmitPostComponent } from './pages/submit-post/submit-post.component';
import { TagsComponent } from './pages/tags/tags.component';

const routes: Routes = [
  {
    path: '',component: PostsComponent
  },
  {
    path:'tags',component:  TagsComponent
  },
  {
    path:'categories',component:  CategoriesComponent
  },
  {
    path:'category/:slug/:id',component:PostsByCategoryComponent
  },
  {
    path:'tag/:slug/:id',component:PostsByTagComponent
  },
  {
    path:'post/:slug/:id',component: PostDetailComponent
  },
  {
    path:'submit',component: SubmitPostComponent,
    canActivate: [RoleGuard],
    data: {
      roles: [
        { authority: RoleNames.ROLE_ADMIN},
        { authority: RoleNames.ROLE_USER}
      ]
    }
  },
  
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class HomeRoutingModule { }
