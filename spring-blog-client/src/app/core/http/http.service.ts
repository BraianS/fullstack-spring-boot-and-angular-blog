import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { BaseError } from '../interface/core.interface';

@Injectable({
  providedIn: 'root'
})
export class HttpService {

  constructor(private http: HttpClient) { }

  get(url: string, body: Object={}) :Observable<any> {
    return this.http.get(`${environment.baseUrl}`+url,body)
      .pipe(catchError(this.handlerError));
  }

  post(url:string, body: Object={}): Observable<any>{
    return this.http.post(`${environment.baseUrl}`+url, body)
      .pipe(catchError(this.handlerError));
  }

  put(url: string, body: Object= {}): Observable<any>{
    return this.http.put(`${environment.baseUrl}`+url, body)
    .pipe(catchError(this.handlerError));
  }

  deleteById(url: string): Observable<any>{
    return this.http.delete(`${environment.baseUrl}`+url)
    .pipe(catchError(this.handlerError));
  }

  handlerError(error: HttpErrorResponse){
    let errorMessage:BaseError = new BaseError();
    if(error.error instanceof ErrorEvent){
      errorMessage.message = `${error.error.message}`;
    }  else {
      errorMessage.status = `${error.status}`;
      errorMessage.message = `${error.error.message}`;
      errorMessage.detail = `${error.error.detail}`;
      errorMessage.timestamp =`${error.error.timestamp}`;
    }
    console.log(error);
    return throwError(errorMessage);
  }
}
