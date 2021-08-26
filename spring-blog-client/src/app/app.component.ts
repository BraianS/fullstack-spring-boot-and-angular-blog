import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  showHeader:boolean=false;
  title = 'spring-blog-client';

  constructor(private router: Router, private activatedRoute: ActivatedRoute){}

  ngOnInit(): void {
   this.router.events.subscribe(event => {
     if(event instanceof NavigationEnd){
       this.showHeader = this.activatedRoute.firstChild?.snapshot.data.showHeader !== false;
       console.log(this.showHeader);
     }
   })

   console.log(this.showHeader);
  }
}
