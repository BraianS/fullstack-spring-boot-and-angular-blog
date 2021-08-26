import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NamesCategoriesOrTagsComponent } from './names-categories-or-tags.component';

describe('NamesCategoriesOrTagsComponent', () => {
  let component: NamesCategoriesOrTagsComponent;
  let fixture: ComponentFixture<NamesCategoriesOrTagsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NamesCategoriesOrTagsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NamesCategoriesOrTagsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
