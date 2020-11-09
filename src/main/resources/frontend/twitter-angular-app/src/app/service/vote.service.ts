import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {VotePayload} from "../add-vote/vote-payload";
const angularHost = 'http://localhost:8080/api/votes/';

@Injectable({
  providedIn: 'root'
})
export class VoteService {

  constructor(private http:HttpClient) { }

  vote(votePayload:VotePayload):Observable<any>{
return this.http.post(angularHost, votePayload)
  }
}
