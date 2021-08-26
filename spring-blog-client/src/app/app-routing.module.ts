import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NotFoundComponent } from './core/modules/not-found/not-found.component';

const routes: Routes = [
  {
    path: '', loadChildren: () => import('./modules/home/home-routing.module').then(module => module.HomeRoutingModule)
  },
  {
    path: 'user', loadChildren: () => import('./modules/user/user-routing.module').then(module => module.UserRoutingModule)
  },
  {
    path: 'login', loadChildren: () => import('./modules/login/login-routing.module').then(module => module.LoginRoutingModule),
    data: { showHeader: false }
  },
  {
    path: '404', component: NotFoundComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
