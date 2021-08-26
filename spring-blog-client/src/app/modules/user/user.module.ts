import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ErrorModule } from 'src/app/core/modules/error/error.module';
import { PostPaginationModule } from 'src/app/shared/components/post-pagination/post-pagination.module';
import { AdminComponent } from './pages/admin/admin.component';
import { EditUserProfileComponent } from './pages/edit-user-profile/edit-user-profile.component';
import { UserCommentsComponent } from './pages/user-comments/user-comments.component';
import { UserPostsComponent } from './pages/user-posts/user-posts.component';
import { UserRoutingModule } from './user-routing.module';
import { UserComponent } from './user.component';


@NgModule({
  declarations: [
   UserComponent,
   UserPostsComponent,
   UserCommentsComponent,
   EditUserProfileComponent,
   AdminComponent
  ],
  imports: [
    CommonModule,
    UserRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    ErrorModule,
    PostPaginationModule
  ],
  exports: [
  ]
})
export class UserModule { }
