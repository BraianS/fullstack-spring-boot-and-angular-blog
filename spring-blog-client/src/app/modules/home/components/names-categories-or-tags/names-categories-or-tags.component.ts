import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { IdAndTitleAndSlugResponse } from '../../interface/home.interface';

@Component({
  selector: 'names-categories-or-tags',
  templateUrl: './names-categories-or-tags.component.html',
  styleUrls: ['./names-categories-or-tags.component.scss']
})
export class NamesCategoriesOrTagsComponent implements OnInit {

  @Input("tagOrCategory") tagOrCategory: IdAndTitleAndSlugResponse[] = [];
  @Input("route") route: string = '';

  constructor(
    private router: Router
  ) { }

  ngOnInit(): void {  
  }

  onRouter(slug: any, id: any) {
    this.router.navigate(['/' + this.route + '/' + slug + '/' + id]);
  }

}
