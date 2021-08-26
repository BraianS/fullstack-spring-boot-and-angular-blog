import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RoleGuard } from 'src/app/core/guards/role-guard';
import { RoleNames } from 'src/app/core/interface/core.interface';
import { AdminComponent } from './pages/admin/admin.component';
import { EditUserProfileComponent } from './pages/edit-user-profile/edit-user-profile.component';
import { UserCommentsComponent } from './pages/user-comments/user-comments.component';
import { UserPostsComponent } from './pages/user-posts/user-posts.component';
import { UserComponent } from './user.component';

const routes: Routes = [
  {
    path: ':username', component: UserComponent,
    children: [
      {
        path: 'submitted', component: UserPostsComponent
      },
      {
        path: 'comments', component: UserCommentsComponent
      },
      {
        path: 'edit', component: EditUserProfileComponent,
        canActivate: [RoleGuard],
        data: {
          roles: [
            { authority: RoleNames.ROLE_USER },
            { authority: RoleNames.ROLE_ADMIN },
          ]
        }
      },
      {
        path: 'admin', component: AdminComponent,
        canActivate: [RoleGuard],
        data: {
          roles: [           
            { authority: RoleNames.ROLE_ADMIN },
          ]
        }        
      },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
