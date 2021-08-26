import { HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CategoryEndPoints } from 'src/app/core/conts';
import { HttpService } from 'src/app/core/http/http.service';
import { IdAndTitleAndSlugResponse } from 'src/app/core/interface/core.interface';
import { TitlePayload } from '../interface/home.interface';

@Injectable({
  providedIn: 'root'
})
export class CategoryService  {

  constructor(private http:HttpService) { }
  
  findById(id: number, page:number,size:number): Observable<any> {
   return this.http.get(`${CategoryEndPoints.CATEGORY}`+id+"?page="+page+"&?size="+size);
  }
  save(titlePayload: TitlePayload) {
   return this.http.post(`${CategoryEndPoints.CATEGORY}`,titlePayload);
  }
  deleteById(id: number): Observable<any> {
   return this.http.deleteById(`${CategoryEndPoints.CATEGORY}`+id);
  }
  update(id: number, titlePayload: TitlePayload): Observable<any> {
   return this.http.put(`${CategoryEndPoints.CATEGORY}`+id,titlePayload);
  }
  findAll(): Observable<IdAndTitleAndSlugResponse[]> {
   return this.http.get(`${CategoryEndPoints.CATEGORY}`);
  }

  searchTitleLimitBy10(title:string): Observable<any>{
    const params = new HttpParams().set('title',title);
    return  this.http.get(`${CategoryEndPoints.SEARCH_TITLE}`,{params:params});
  }
}
