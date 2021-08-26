import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostPaginationComponent } from './post-pagination.component';

describe('PostPaginationComponent', () => {
  let component: PostPaginationComponent;
  let fixture: ComponentFixture<PostPaginationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PostPaginationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PostPaginationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
