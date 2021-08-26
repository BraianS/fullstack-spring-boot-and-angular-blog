import { HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserEndPoints } from 'src/app/core/conts';
import { HttpService } from 'src/app/core/http/http.service';
import { UserSummary } from '../interface/user.interface';

@Injectable({
  providedIn: 'root'
})
export class UserService  {

  constructor(private http:HttpService) { }

  getMe(): Observable<any> {
    return this.http.get(`${UserEndPoints.USER}+me`);
  }

  findPostsByUsername(username:string,page:number, size:number):Observable<any>{
    return this.http.get(`${UserEndPoints.USER}`+username+`/submitted/?page=`+page+`&?size=`+size);
  }

  findCommentsByUsername(username:string,page:number, size:number):Observable<any>{
    return this.http.get(`${UserEndPoints.USER}`+username+`/comments/?page=`+page+`&?size=`+size);
  }

  findById(id: number): Observable<any> {
    return this.http.get(`${UserEndPoints.USER}`+id);
  }
  deleteById(id: number): Observable<any> {
    return this.http.deleteById(`${UserEndPoints.USER}`+id);
  }
  
  update(id:number,body: any): Observable<any> {
    return this.http.put(`${UserEndPoints.USER}`+id,body);
  }

  findByUsername(username:string):Observable<UserSummary>{
    return this.http.get(`${UserEndPoints.USER}`+username);
  }

  checkEmailAvailability(email:string):Observable<any>{
    const params = new HttpParams().set('email',email);
    return this.http.get(`${UserEndPoints.CHECK_EMAIL_AVAILABILITY}`,{params:params});
  }

  checkUsernameAvailability(username:string):Observable<any>{
    const params = new HttpParams().set('username',username);
    return this.http.get(`${UserEndPoints.CHECK_USERNAME_AVAILABILITY}`,{params: params});
  }

  updatePassword(username:string,body: any): Observable<any> {
    return this.http.put(`${UserEndPoints.USER}`+username+`/changePassword`,body);
  }

  updateEmail(username:string,body: any): Observable<any> {
    return this.http.put(`${UserEndPoints.USER}`+username+`/changeEmail`,body);
  }

  updateName(username:string,body: any): Observable<any> {
    return this.http.put(`${UserEndPoints.USER}`+username+`/changeName`,body);
  }
}
