import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {NewCommentPayload} from "../create-comment/newCommentPayload";

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
}
