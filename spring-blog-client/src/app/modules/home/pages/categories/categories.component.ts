import { Component, OnInit } from '@angular/core';
import { IdAndTitleAndSlugResponse } from '../../interface/home.interface';
import { CategoryService } from '../../shared/category.service';

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.scss']
})
export class CategoriesComponent implements OnInit {

  categories: IdAndTitleAndSlugResponse[] = [];

  constructor(
    private categoryService: CategoryService
  ) { }

  ngOnInit(): void {
    this.findAll();
  }

  findAll() {
    this.categoryService.findAll().subscribe(categories => {
      this.categories = categories;
    })
  }

}
