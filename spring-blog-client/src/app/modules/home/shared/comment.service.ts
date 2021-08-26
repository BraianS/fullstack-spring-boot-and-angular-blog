import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommentEndPoints } from 'src/app/core/conts';
import { HttpService } from 'src/app/core/http/http.service';
import { ContentRequest } from '../interface/home.interface';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private http: HttpService) { }
  findById(id: number): Observable<any> {
    return this.http.get(`${CommentEndPoints.COMMENT}`+id);
  }
  save(postId:string, contentRequest: ContentRequest): Observable<any> {   
    return this.http.post(`${CommentEndPoints.COMMENT}`+postId, contentRequest);
  }

  saveSubComment(postId:string,commentId:number, contentRequest: ContentRequest): Observable<any> {
    return this.http.post(`${CommentEndPoints.COMMENT}`+postId+`/subComment/`+commentId, contentRequest);
  }

  deleteById(id: number): Observable<any> {
    return this.http.put(`${CommentEndPoints.COMMENT}`+id+"/delete");
  }
  update(id: number, contentRequest: ContentRequest ): Observable<any> {
    return this.http.put(`${CommentEndPoints.COMMENT}`+id,contentRequest);
  }
  findAll(): Observable<any> {
    return this.http.get(`${CommentEndPoints.COMMENT}`);
  }
}
