import { Component, OnInit } from '@angular/core';
import { IdAndTitleAndSlugResponse } from 'src/app/core/interface/core.interface';
import { TagService } from '../../shared/tag.service';

@Component({
  selector: 'app-tags',
  templateUrl: './tags.component.html',
  styleUrls: ['./tags.component.scss']
})
export class TagsComponent implements OnInit {

  tags: IdAndTitleAndSlugResponse[] = [];

  constructor(
    private tagService: TagService
  ) { }

  ngOnInit(): void {
    this.findAll();
  }

  findAll() {
    this.tagService.findAll().subscribe(tags => {
      this.tags = tags;
    })
  }

}
