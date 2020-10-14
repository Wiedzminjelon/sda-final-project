import {Injectable} from '@angular/core';
import {SignupRequestPayload} from '../sign-up/signup-request.payload';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {LogInRequestPayload} from "../log-in/log-in-request.payload";

const angularHost = 'http://localhost:8080/auth';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) {
  }

  signup(signupRequestPayload: SignupRequestPayload): Observable<any> {
    return this.http.post(angularHost + '/signup', signupRequestPayload);
  }


  login(loginRequestPayload: LogInRequestPayload): Observable<any> {
    return this.http.post(angularHost + '/login', loginRequestPayload);
  }
}
