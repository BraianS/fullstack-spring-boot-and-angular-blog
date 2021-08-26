import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PostsService } from 'src/app/modules/home/shared/posts.service';
import { UserService } from '../../shared/user.service';

@Component({
  selector: 'app-user-posts',
  templateUrl: './user-posts.component.html',
  styleUrls: ['./user-posts.component.scss']
})
export class UserPostsComponent implements OnInit {

  pageNumber:number = 0;
  pageSize:number = 20;
  pagination:any;
  username:string = "";
  totalPages: Array<number> = Array<number>();

  constructor(
    private postService:PostsService,
    private userService:UserService,
    private activedRoute:ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.activedRoute.parent?.params.subscribe(params => {
     this.username = params.username;
    }) 
    this.findPostsByUsername();
  }

  findPostsByUsername() {
    this.userService.findPostsByUsername(this.username,this.pageNumber, this.pageSize).subscribe(pagination => {
      this.pagination = pagination;
      this.totalPages = new Array(this.pagination['totalPages'])
    })
  }

  setPage(page:any){
    this.userService.findPostsByUsername(this.username,page, this.pageSize).subscribe(pagination => {
      this.pagination = pagination;
      this.pageNumber = page;
    })
  }

}
