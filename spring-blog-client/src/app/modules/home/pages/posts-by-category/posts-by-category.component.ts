import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Pagination } from 'src/app/core/conts';
import { CategoryService } from '../../shared/category.service';

@Component({
  selector: 'app-posts-by-category',
  templateUrl: './posts-by-category.component.html',
  styleUrls: ['./posts-by-category.component.scss']
})
export class PostsByCategoryComponent implements OnInit {

  pageNumber: number = Pagination.PAGE_NUMBER;
  pageSize: number = Pagination.PAGE_SIZE;

  categoryId: number = 0;

  totalPages: Array<number> = Array<number>();
  pagination: any;

  constructor(
    private activatedRouter: ActivatedRoute,
    private categoryService: CategoryService) { }

  ngOnInit(): void {
    this.activatedRouter.params.subscribe(params => {
      this.categoryId = params.id;
      this.findPostsByCategoryId(this.categoryId);
    })
  }

  findPostsByCategoryId(categoryId: number) {
    this.categoryId = categoryId;
    this.categoryService.findById(this.categoryId, this.pageNumber,this.pageSize).subscribe(pagination => {
      this.totalPages = new Array(pagination['totalPages']);
      this.pagination = pagination;
    })
    this.categoryId = categoryId;
  }

  setPage(page: any) {
    this.categoryService.findById(this.categoryId, page,this.pageSize).subscribe(pagination => {
      this.pagination = pagination;
      this.pageNumber = page;
    });
  }

}
