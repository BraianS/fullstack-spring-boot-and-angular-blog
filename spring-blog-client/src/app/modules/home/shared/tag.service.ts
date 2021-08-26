import { HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TagsEndPoints } from 'src/app/core/conts';
import { HttpService } from 'src/app/core/http/http.service';
import { IdAndTitleAndSlugResponse } from 'src/app/core/interface/core.interface';
import { TitlePayload } from '../interface/home.interface';

@Injectable({
  providedIn: 'root'
})
export class TagService  {

  constructor(protected http: HttpService) {
   
  }

  findById(id:number, page:number, size:number):Observable<any> {
    return  this.http.get(`${TagsEndPoints.TAG}`+id+"?page="+page+"&size="+size);
   }

  save(titlePayload: TitlePayload): Observable<any> {
    return this.http.post(`${TagsEndPoints.TAG}`, titlePayload)
  }
  deleteById(id: number): Observable<any> {
    return this.http.deleteById(`${TagsEndPoints.TAG}` + id)
  }
  update(id: number, titlePayload: any): Observable<any> {
    return this.http.put(`${TagsEndPoints.TAG}`+id, titlePayload)
  }

  findAll(): Observable<IdAndTitleAndSlugResponse[]> {
    return this.http.get(`${TagsEndPoints.TAG}`)
  }

  searchTitleLimitBy10(title: string): Observable<any> {
    const params = new HttpParams().set('title', title);
    return this.http.get(`${TagsEndPoints.SEARCH_TITLE}`, { params: params });
  }
}
