import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { IdAndTitleAndSlugResponse } from 'src/app/core/interface/core.interface';
import { TitleValidation } from 'src/app/core/validators/Validator';
import { CategoryService } from 'src/app/modules/home/shared/category.service';
import { TagService } from 'src/app/modules/home/shared/tag.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent implements OnInit {

  titleTagForm: FormGroup;
  titleCategoryForm: FormGroup;

  tagUpdate: IdAndTitleAndSlugResponse = {} as IdAndTitleAndSlugResponse;
  categoryUpdate: IdAndTitleAndSlugResponse = {} as IdAndTitleAndSlugResponse;

  tags: IdAndTitleAndSlugResponse[] = [];
  categories: IdAndTitleAndSlugResponse[] = [];

  isEdit: Boolean = false;
  isEditCategory:Boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private categoryService: CategoryService,
    private tagService: TagService,
    private toastrService: ToastrService
  ) {
    this.titleCategoryForm = this.formBuilder.group({
      title: ['', TitleValidation]
    });

    this.titleTagForm = this.formBuilder.group({
      title: ['', TitleValidation]
    });
  }

  ngOnInit(): void {
    this.getAllTags();
    this.getAllCategories();
  }

  get titleTagControl(): { [key: string]: AbstractControl } {
    return this.titleTagForm.controls;
  }

  get titleCategoryControl(): { [key: string]: AbstractControl } {
    return this.titleCategoryForm.controls;
  }

  cancelTitleTag() {
    this.isEdit = false;
    this.titleTagForm.reset();
  }

  cancelTitleCategory() {
    this.isEditCategory = false;
    this.titleCategoryForm.reset();
  }

  saveTag() {
    this.tagService.save(this.titleTagForm.value).subscribe(response => {
      this.toastrService.success(response.message);
      this.cancelTitleTag();
      this.getAllTags();
    }, (error => {
      this.toastrService.error(error.message || error.detail);
    }))
  }

  saveCategory() {
    this.categoryService.save(this.titleCategoryForm.value).subscribe(response => {
      this.toastrService.success(response.message);
      this.cancelTitleCategory();
      this.getAllCategories();
    }, (error => {
      this.toastrService.error(error.message || error.detail);
    }))
  }

  getAllTags() {
    this.tagService.findAll().subscribe(tags => {
      this.tags = tags;
    })
  }

  getAllCategories() {
    this.categoryService.findAll().subscribe(categories => {
      this.categories = categories;
    })
  }

  editTag(tag: IdAndTitleAndSlugResponse) {
    this.isEdit = true;
    this.tagUpdate = tag;
    this.titleTagForm.setValue({ title: tag.title })
  }

  onSubmit() {
    if (this.isEdit) {
      this.update();
    } else {
      this.saveTag();
    }
  }

  update() {
    this.isEdit = false;
    this.tagService.update(this.tagUpdate.id, this.titleTagForm.value).subscribe(response => {
      this.toastrService.success(response.message);
      this.getAllTags();
      this.cancelTitleTag();
    })
  }

  deleteTag(id: number) {
    this.tagService.deleteById(id).subscribe(response => {
      this.toastrService.success(response.message);
      this.getAllTags();
      console.log(response);
    })
  }

  onSubmitCategory(){
    if (this.isEditCategory) {
      this.updateCategory();
    } else {
      this.saveCategory();
    }
  }

  editCategory(category:IdAndTitleAndSlugResponse){
    this.isEditCategory = true;
    this.categoryUpdate = category;
    this.titleCategoryForm.setValue({ title: category.title })
  }

  updateCategory(){
    this.isEditCategory = false;
    this.categoryService.update(this.categoryUpdate.id, this.titleCategoryForm.value).subscribe(response => {
      this.toastrService.success(response.message);
      this.getAllCategories();
      this.cancelTitleCategory();
    })
  }

  deleteCategory(id:number){
    this.categoryService.deleteById(id).subscribe(response => {
      this.toastrService.success(response.message);
      this.getAllCategories();     
    })
  }

}
