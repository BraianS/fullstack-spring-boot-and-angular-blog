import { Component, OnInit } from '@angular/core';
import { Pagination } from 'src/app/core/conts';
import { PostsService } from '../../shared/posts.service';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.scss']
})
export class PostsComponent implements OnInit {  

  pageNumber: number = Pagination.PAGE_NUMBER;
  pageSize: number = Pagination.PAGE_SIZE;

  pagination: any = [];
  totalPages: Array<number> = Array<number>();

  constructor(
    private postService: PostsService
  ) { }

  ngOnInit(): void {
    this.postService.getAllPostsOrderByCreatedAtAsc(this.pageNumber, this.pageSize).subscribe(postsResponse => {
     this.pagination = postsResponse;
      this.totalPages = new Array(this.pagination['totalPages']);
    })
  }

  setPage(page: any) {
    this.postService.getAllPostsOrderByCreatedAtAsc(page).subscribe(postsResponse => {
      this.pagination = postsResponse;
      this.pageNumber = page;
    });
  } 
  
}
