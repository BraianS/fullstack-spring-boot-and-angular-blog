import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { AuthenticateService } from "src/app/modules/login/shared/authenticate.service";
import { TokenService } from "src/app/modules/login/shared/token.service";

@Injectable()
export class JwtInterceptor implements HttpInterceptor{

    constructor(private authenticateService: AuthenticateService,
        private tokenService: TokenService){}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
       let token =  this.tokenService.getToken();
       if(token) {
           req = req.clone({
               setHeaders: {
                   Authorization: `Bearer ${token}`
               }
           });
       }

       return next.handle(req);
    }

}