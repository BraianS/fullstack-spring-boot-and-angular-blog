import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { JwtAuthenticatedResponse } from 'src/app/core/interface/core.interface';
import { AuthenticateService } from 'src/app/modules/login/shared/authenticate.service';
import { TokenService } from 'src/app/modules/login/shared/token.service';

@Component({
  selector: 'header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  user: JwtAuthenticatedResponse = {} as JwtAuthenticatedResponse;
  isToggle:Boolean = false;

  /* @ViewChild('navMenu') navMenu!: ElementRef; */
  @ViewChild('navMenu') navMenu!: ElementRef;
  @ViewChild('navBurger') navBurger!: ElementRef;

  constructor(
    private tokenService: TokenService,
    private authenticateService:AuthenticateService) { }

  ngOnInit(): void {
    this.user = this.tokenService.getUser();
  }

  logout() {
    this.authenticateService.logout(); 
    this.user = this.tokenService.getUser();  
    location.reload();
  }

  toggle(){
  this.navMenu.nativeElement.classList.toggle('is-active');
  this.navBurger.nativeElement.classList.toggle('is-active');
  }

}
