import {EventEmitter, Injectable, Output} from '@angular/core';
import {SignupRequestPayload} from '../sign-up/signup-request.payload';
import {Observable, throwError} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {LogInRequestPayload} from "../log-in/log-in-request.payload";
import {LoginResponse} from "../log-in/log-in-response.payload";
import {map, tap} from "rxjs/operators";
import {LocalStorageService} from "ngx-webstorage";

const angularHost = 'http://localhost:8080/auth';

@Injectable({
  providedIn: 'root'
})

export class AuthService {

  @Output() loggedIn: EventEmitter<boolean> = new EventEmitter();
  @Output() username: EventEmitter<string> = new EventEmitter();

  refreshTokenPayload = {
    refreshToken: this.getRefreshToken(),
    username: this.getUserName()
  }

  constructor(private http: HttpClient, private localStorage: LocalStorageService) {
  }

  signup(signupRequestPayload: SignupRequestPayload): Observable<any> {
    return this.http.post(angularHost + '/signup', signupRequestPayload);
  }


  login(loginRequestPayload: LogInRequestPayload):Observable<boolean> {
    return this.http.post<LoginResponse>(angularHost + '/login', loginRequestPayload).pipe(map(data => {
      this.localStorage.store('authenticationToken', data.authenticationToken)
      this.localStorage.store('username', data.username)
      this.localStorage.store('refreshtoken', data.refreshToken)
      this.localStorage.store('expiresAt', data.expiresAt);

      return true;
    }));
  }

  getJwtToken(){
    return this.localStorage.retrieve('authenticationToken');
  }

  refreshToken() {
    return this.http.post<LoginResponse>(angularHost + 'refresh/token',
      this.refreshTokenPayload)
      .pipe(tap(response => {
        this.localStorage.clear('authenticationToken');
        this.localStorage.clear('expiresAt');

        this.localStorage.store('authenticationToken',
          response.authenticationToken);
        this.localStorage.store('expiresAt', response.expiresAt);
      }));
  }

  getUserName() {
    return this.localStorage.retrieve('username');
  }
  getRefreshToken() {
    return this.localStorage.retrieve('refreshToken');
  }

  isLoggedIn(): boolean {
    return this.getJwtToken() != null;
  }

  logout() {
    this.http.post(angularHost + 'auth/logout', this.refreshTokenPayload,
      { responseType: 'text' })
      .subscribe(data => {
        console.log(data);
      }, error => {
        throwError(error);
      })
    this.localStorage.clear('authenticationToken');
    this.localStorage.clear('username');
    this.localStorage.clear('refreshToken');
    this.localStorage.clear('expiresAt');
  }

}

