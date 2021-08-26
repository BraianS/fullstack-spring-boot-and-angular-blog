import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Pagination } from 'src/app/core/conts';
import { TagService } from '../../shared/tag.service';

@Component({
  selector: 'app-posts-by-tag',
  templateUrl: './posts-by-tag.component.html',
  styleUrls: ['./posts-by-tag.component.scss']
})
export class PostsByTagComponent implements OnInit {

  pageNumber: number = Pagination.PAGE_NUMBER;
  pageSize: number = Pagination.PAGE_SIZE;

  tagId: number = 0;

  totalPages: Array<number> = Array<number>();
  pagination: any;

  constructor(
    private activatedRouter: ActivatedRoute,
    private tagService: TagService) { }

  ngOnInit(): void {
    this.activatedRouter.params.subscribe(params => {
      this.tagId = params.id;
      this.findPostsByTagId(this.tagId);
    })
  }

  findPostsByTagId(tagId: number) {
    this.tagId = tagId;
    this.tagService.findById(this.tagId, this.pageNumber, this.pageSize).subscribe(pagination => {
      this.totalPages = new Array(pagination['totalPages']);
      this.pagination = pagination;
    })
    this.tagId = tagId;
  }

  setPage(pageNumber: any) {
    this.tagService.findById(this.tagId, pageNumber, this.pageSize).subscribe(pagination => {
      this.pagination = pagination;
      this.pageNumber = pageNumber;
    });
  }

}
