
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AngularEditorModule } from '@kolkov/angular-editor';
import { NgSelectModule } from '@ng-select/ng-select';
import { ErrorModule } from 'src/app/core/modules/error/error.module';
import { PostPaginationModule } from 'src/app/shared/components/post-pagination/post-pagination.module';
import { HomeRoutingModule } from './home-routing.module';
import { HomeComponent } from './home.component';
import { CategoriesComponent } from './pages/categories/categories.component';
import { PostDetailComponent } from './pages/post-detail/post-detail.component';
import { PostsByCategoryComponent } from './pages/posts-by-category/posts-by-category.component';
import { PostsByTagComponent } from './pages/posts-by-tag/posts-by-tag.component';
import { PostsComponent } from './pages/posts/posts.component';
import { SubmitPostComponent } from './pages/submit-post/submit-post.component';
import { TagsComponent } from './pages/tags/tags.component';
import { NamesCategoriesOrTagsComponent } from './components/names-categories-or-tags/names-categories-or-tags.component';



@NgModule({
  declarations: [
    PostsByCategoryComponent,
    PostsByTagComponent,
    HomeComponent,
    PostsComponent,
    PostDetailComponent,
    SubmitPostComponent,
    TagsComponent,
    CategoriesComponent,
    NamesCategoriesOrTagsComponent,
  ],
  imports: [
    CommonModule,
    HomeRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    NgSelectModule,
    ErrorModule,
    PostPaginationModule,
    AngularEditorModule,
  ],
  providers:[]
})
export class HomeModule { }
