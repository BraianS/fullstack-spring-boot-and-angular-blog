import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { AuthenticateEndPoints } from 'src/app/core/conts';
import { HttpService } from 'src/app/core/http/http.service';
import { JwtAuthenticatedResponse } from 'src/app/core/interface/core.interface';
import { LoginPayload, SignInPayload } from '../interface/login.interface';
import { TokenService } from './token.service';

@Injectable({
  providedIn: 'root'
})
export class AuthenticateService {

  constructor(
    private http: HttpService,
    private tokenService: TokenService,
    private router: Router) { }

  signIn(signInPayload: SignInPayload): Observable<any> {
    return this.http.post(`${AuthenticateEndPoints.SIGN_IN}`, signInPayload)
      .pipe(
        map((response: JwtAuthenticatedResponse) => {
          this.tokenService.saveToken(response.accessToken);
          this.tokenService.saveUser(response);
          return response;
        })
      )
  }

  signUp(loginPayload: LoginPayload): Observable<any> {
    return this.http.post(`${AuthenticateEndPoints.SIGN_UP}`, loginPayload)
  }

  logout() {
    localStorage.removeItem("USER_KEY");
    localStorage.removeItem("TOKEN_KEY");
    this.router.navigate(['']);
    window.location.reload();
  }
}
