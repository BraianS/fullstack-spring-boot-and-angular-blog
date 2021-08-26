import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { CommentService } from 'src/app/modules/home/shared/comment.service';
import { TokenService } from 'src/app/modules/login/shared/token.service';
import { CommentRequest } from '../../interface/user.interface';
import { UserService } from '../../shared/user.service';

@Component({
  selector: 'app-user-comments',
  templateUrl: './user-comments.component.html',
  styleUrls: ['./user-comments.component.scss']
})
export class UserCommentsComponent implements OnInit {

  username:string = "";
  page:number = 0;
  size:number = 20;
  pageable:any;
  totalPages:Array<Number> = new Array<Number>();
  commentForm:FormGroup;
  toggleEditComment:Boolean = false
  IsUserlogged:Boolean = true;
  user:any;
  
  commentRequest:CommentRequest = new CommentRequest();

  commentSelected:number = 0;

  constructor(
    private userService:UserService,
    private commentService:CommentService,
    private activatedRouter:ActivatedRoute,
    private formBuilder: FormBuilder,
    private tokenService:TokenService
  ) {
    this.commentForm = this.formBuilder.group({
      content: [Validators.required]
    })
   }

  ngOnInit(): void {
    this.activatedRouter.parent?.params.subscribe(params => {
      this.username = params.username;
    });
    this.getAllCommentsByUsername();
    this.checkUserIslogged();
  }

  checkUserIslogged(){
   this.user = this.tokenService.getUser();
   if(this.user.username){
     this.IsUserlogged = true;
    } else {
     this.IsUserlogged = false
    }
  }

  getAllCommentsByUsername(){
    this.userService.findCommentsByUsername(this.username,this.page, this.size).subscribe(pageable => {
      this.pageable = pageable;
      console.log(pageable);
      this.totalPages =  new Array(pageable['totalPages']);
    })
  }

  setPage(page:number, event:any){
    this.userService.findPostsByUsername(this.username,page, this.size).subscribe(response => {
      this.pageable = response;
      this.page = page;
    })
  }

  update(comment:any){
   this.commentService.update(comment.id, this.commentRequest).subscribe(response => {
    this.getAllCommentsByUsername();
    this.commentSelected = 0 ;
    this.commentRequest = new CommentRequest();
    this.toggleEditComment = false;
   })
  }

  editComment(comment:any){
    this.toggleEditComment = true;
    this.commentSelected = comment.id ;
    this.commentRequest.content = comment.content;
  }

  deleteComment(id:number){
    this.commentService.deleteById(id).subscribe(response => {
      console.log(response);
    })
  }

  updateComment(id:number){
    this.commentService.update(id,this.commentForm.value).subscribe(response => {
      console.log(response);
    })
  }

  cancelar(){
    this.toggleEditComment = false;
    this.commentSelected = 0;
    this.commentRequest = new CommentRequest();
  }

}
