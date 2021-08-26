import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostsByCategoryComponent } from './posts-by-category.component';

describe('PostsByCategoryComponent', () => {
  let component: PostsByCategoryComponent;
  let fixture: ComponentFixture<PostsByCategoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PostsByCategoryComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PostsByCategoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
