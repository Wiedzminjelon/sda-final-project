import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {PostModel} from "../models/post-model";
import {NewPostRequestPayload} from "../create-post/newPostRequestPayload";
const angularHost= 'http://localhost:8080/api/posts';


@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private http: HttpClient) { }

  newPost(newPostRequestPayload: NewPostRequestPayload): Observable<any> {
    return this.http.post(angularHost, newPostRequestPayload);
  }

  getAllPosts() : Observable<Array<PostModel>>{
    return this.http.get<Array<PostModel>>( angularHost + '/all');
  }

  getPost(id: number):Observable<PostModel> {
    return this.http.get<PostModel>(angularHost + id);
  }

}
