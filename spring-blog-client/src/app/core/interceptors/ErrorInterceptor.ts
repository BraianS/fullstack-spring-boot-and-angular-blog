import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { Observable } from "rxjs";
import { catchError } from "rxjs/operators";
import { AuthenticateService } from "src/app/modules/login/shared/authenticate.service";

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {

    public constructor(
        private route: Router,
        private authenticateService:AuthenticateService){}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
       return next.handle(req).pipe(catchError(err => {
           if(err.status === 401 || err.status === 403) {
             /*   this.route.navigate(["login/signin"]); */
             this.authenticateService.logout();
           }
           return next.handle(req);
       }))
    }    
}