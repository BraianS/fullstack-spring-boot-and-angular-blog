import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AngularEditorConfig } from '@kolkov/angular-editor';
import { NgSelectConfig } from '@ng-select/ng-select';
import { AppResponse, BaseError, IdAndTitleAndSlugResponse } from 'src/app/core/interface/core.interface';
import { ContentValidation, TitleValidation } from 'src/app/core/validators/Validator';
import { Status } from '../../interface/home.interface';
import { CategoryService } from '../../shared/category.service';
import { PostsService } from '../../shared/posts.service';
import { TagService } from '../../shared/tag.service';

@Component({
  selector: 'app-submit-post',
  templateUrl: './submit-post.component.html',
  styleUrls: ['./submit-post.component.scss']
})
export class SubmitPostComponent implements OnInit {

  postForm: FormGroup;
  status: Status[] = [Status.PUBLIC, Status.PRIVATE];

  tags: IdAndTitleAndSlugResponse[] = [];
  categories: IdAndTitleAndSlugResponse[] = [];

  selectedTags: [] = [];

  isLoading:Boolean = true;
  appResponse:AppResponse = {} as AppResponse;
  errorBase:BaseError = new BaseError();

  constructor(
    private formBuilder: FormBuilder,
    private postService: PostsService,
    private tagService: TagService,
    private categoryService: CategoryService,
    private config: NgSelectConfig) {

    this.postForm = this.formBuilder.group({
      title: ['', TitleValidation],
      content: ['', ContentValidation],
      status: [this.status[0]],
      tags: ['', Validators.required],
      categories: ['', Validators.required]
    });
    this.config.notFoundText = 'Custom not found';
  }

  ngOnInit(): void {

  }

  get postControl(): { [key: string]: AbstractControl } {
    return this.postForm.controls;
  }

  onSubmit() {
    this.isLoading = true;
    this.postService.save(this.postForm.value).subscribe(result => {
      this.isLoading = false;
      this.appResponse = result;
      this.clearForm();
      setTimeout(() => {
          this.appResponse = {} as AppResponse;
      }, 4000);
    }, (error => {
      this.errorBase = error;
    }))
  }

  clearForm() {
    this.postForm.reset();
  }

  onSearchTagTitle(event: any) {
    this.tagService.searchTitleLimitBy10(event.target.value).subscribe((tags: any) => {
      this.tags = tags;
    })
  }

  onSearchCategoryTitle(event: any) {
    this.categoryService.searchTitleLimitBy10(event.target.value).subscribe((tags: any) => {
      this.categories = tags;
    })
  }

  clearInput() {
    this.tags = [];
  }

  clearCategories() {
    this.categories = [];
  }

  onChange(event: any) {
    console.log(event);
  }

  vava:string = "aa";

  editorConfig: AngularEditorConfig = {
    editable: true,
    spellcheck: true,
    height: '15rem',
    minHeight: '5rem',
    placeholder: 'Enter text here...',
    translate: 'no',
    defaultParagraphSeparator: 'p',   
    toolbarHiddenButtons: [
      ['bold']
      ],
    customClasses: [
      {
        name: "quote",
        class: "quote",
      },
      {
        name: 'redText',
        class: 'redText'
      },
      {
        name: "titleText",
        class: "titleText",
        tag: "h1",
      },
    ]
  };

}
