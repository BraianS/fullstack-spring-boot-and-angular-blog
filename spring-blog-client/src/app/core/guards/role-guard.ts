import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { TokenService } from 'src/app/modules/login/shared/token.service';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {

  constructor(private tokenService: TokenService, private route: Router) {

  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): 
    boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {

    const user = this.tokenService.getUser();

    if (user) {

      for (let index = 0; index < route.data.roles.length; index++) {
        var role = route.data.roles[index];

        for (let index2 = 0; index2 < user.authorities.length; index2++) {
          var user_role = user.authorities[index2];

          if (role.authority === user_role.authority) {
            return true;
          }
        }
      }
      this.route.navigate(['/']);
      return false;
    }

    this.route.navigate(['/login/signin']);
    return false;
  }


}
