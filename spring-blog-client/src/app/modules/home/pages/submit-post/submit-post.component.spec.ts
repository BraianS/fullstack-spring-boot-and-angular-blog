import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SubmitPostComponent } from './submit-post.component';


describe('AddPostComponent', () => {
  let component: SubmitPostComponent;
  let fixture: ComponentFixture<SubmitPostComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SubmitPostComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SubmitPostComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
