import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import {NewPostRequestPayload} from "./newPostRequestPayload";
const angularHost= 'http://localhost:8080/';

@Injectable({
  providedIn: 'root'
})
export class CreatePostService {

  constructor(private http: HttpClient) { }

  newPost(newPostRequestPayload: NewPostRequestPayload): Observable<any> {
    return this.http.post(angularHost+'api/posts', newPostRequestPayload);
  }
}
