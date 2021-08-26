import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  constructor() { }

  saveToken(token:string){
    localStorage.removeItem("TOKEN_KEY");
    localStorage.setItem("TOKEN_KEY", token);
  }

  getToken(): any {
    return localStorage.getItem("TOKEN_KEY");
  }

  public saveUser(user:any){
    localStorage.removeItem("USER_KEY")
    localStorage.setItem("USER_KEY",JSON.stringify(user));
  }

  public getUser(): any {
    return JSON.parse(localStorage.getItem("USER_KEY") || '{}');
  }
}
