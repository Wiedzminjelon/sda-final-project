import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {NewCommentPayload} from "../create-comment/newCommentPayload";
import {CommentModel} from "../models/comment-model";

const angularHost = 'http://localhost:8080/api/comments';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private http: HttpClient) {
  }

  newComment(newCommentPayload: NewCommentPayload): Observable<any> {
    return this.http.post(angularHost + "/", newCommentPayload);
  }

  showComments(id:number):Observable<Array<CommentModel>> {
    return this.http.get<Array<CommentModel>>(angularHost + "/by-post/" + id);
  }
}
