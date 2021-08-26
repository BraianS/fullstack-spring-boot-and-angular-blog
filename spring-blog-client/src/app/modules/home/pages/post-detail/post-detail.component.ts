import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { TokenService } from 'src/app/modules/login/shared/token.service';
import { CommentResponse, PostDetail } from '../../interface/home.interface';
import { CommentService } from '../../shared/comment.service';
import { PostsService } from '../../shared/posts.service';

@Component({
  selector: 'app-post-detail',
  templateUrl: './post-detail.component.html',
  styleUrls: ['./post-detail.component.scss']
})
export class PostDetailComponent implements OnInit {

  commentForm: FormGroup;
  subCommentForm: FormGroup;

  postDetail: PostDetail = {} as PostDetail;
  comments: CommentResponse[] = [];

  id: number = 0;
  isSubCommentFormEnable: boolean = false;
  commentSelected: number = 0;

  constructor(
    private postService: PostsService,
    private activatedRoute: ActivatedRoute,
    private commentService: CommentService,
    private tokenService: TokenService,
    private formBuilder: FormBuilder) {

    this.commentForm = this.formBuilder.group({
      content: ['', Validators.required]
    });

    this.subCommentForm = this.formBuilder.group({
      content: ['', Validators.required]
    })
  }

  ngOnInit(): void {
    this.findById();
    this.findCommentsByPostId();
  }

  findById() {
    this.activatedRoute.params.subscribe(params => {
      this.id = params.id;

      this.postService.findById(this.id).subscribe(post => {
        this.postDetail = post;
      })
    })
  }

  saveComment() {
    this.commentService.save(this.postDetail.id, this.commentForm.value).subscribe(response => {
      this.clearComment();
      this.findCommentsByPostId();
    })
  }

  clearComment() {
    this.commentForm.reset();
  }

  saveSubComment(commentId: number) {
    this.commentService.saveSubComment(this.postDetail.id, commentId, this.subCommentForm.value).subscribe(response => {
      this.findCommentsByPostId();
      this.subCommentForm.reset();
      this.commentSelected = 0;
    })
  }

  toggleSubCommentForm(): Boolean {
    return this.isSubCommentFormEnable = this.isSubCommentFormEnable !== true;
  }

  isReplyEnable(id: number) {
    this.commentSelected = id;
  }

  findCommentsByPostId() {
    this.commentService.findById(this.id).subscribe(comments => {
      this.comments = comments;
    })
  }

  isUserComment(username: string): Boolean {
    const user = this.tokenService.getUser();
    if (username === user.username) {
      return true
    }
    return false;
  }

  deleteComment(id: number) {
    this.commentService.deleteById(id).subscribe(response => {
      this.findCommentsByPostId();
    })
  }

}
