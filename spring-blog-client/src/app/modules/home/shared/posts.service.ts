import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PostsEndPoints } from 'src/app/core/conts';
import { HttpService } from 'src/app/core/http/http.service';
import { AppResponse } from 'src/app/core/interface/core.interface';
import { PostDetail, PostRequest } from '../interface/home.interface';

@Injectable({
  providedIn: 'root'
})
export class PostsService {

  constructor(private http:HttpService) { }  

  findById(id:number):Observable<PostDetail>{
    return this.http.get(`${PostsEndPoints.FIND_BY_ID}/`+id);
  }

  save(postRequest:PostRequest):Observable<any> {
    return this.http.post(`${PostsEndPoints.SAVE}`, postRequest);
  }

  deleteById(id:number):Observable<AppResponse>{
    return this.http.deleteById(`${PostsEndPoints.DELETE_BY_ID}`+id);
  }

  update(id:number,postRequest:PostRequest):Observable<PostRequest> {
    return this.http.put(`${PostsEndPoints.SAVE}`+id, postRequest);
  }

  getAllPostsOrderByCreatedAtAsc(page?:any, size?:any): Observable<any>{
    return this.http.get(PostsEndPoints.GET_POSTS_ORDER_BY_CREATED_AT_DESC+'?page='+page+'&?size='+size);
  }
  
}
