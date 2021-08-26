import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { PostPaginationComponent } from './post-pagination.component';

@NgModule({
  declarations: [PostPaginationComponent],
  imports: [
    CommonModule,
    RouterModule
  ],
  exports : [
    PostPaginationComponent
  ]
})
export class PostPaginationModule { }
