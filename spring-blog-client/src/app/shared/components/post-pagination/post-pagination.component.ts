import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'post-pagination',
  templateUrl: './post-pagination.component.html',
  styleUrls: ['./post-pagination.component.scss']
})
export class PostPaginationComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {  
  }

  @Output() changePage =  new EventEmitter<Number>();

  pageNumber:number = 0;
  @Input() pagination:any;
  @Input() totalPages: Array<number> = Array<number>();

  setPageChild(index:any){
    this.pageNumber = index;
    this.changePage.emit(this.pageNumber);
  }

}
