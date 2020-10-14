import { Component, OnInit } from '@angular/core';
import {LogInRequestPayload} from "./log-in-request.payload";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../auth/auth.service";

@Component({
  selector: 'app-log-in',
  templateUrl: './log-in.component.html',
  styleUrls: ['./log-in.component.css']
})
export class LogInComponent implements OnInit {
  loginRequestPayload: LogInRequestPayload;
  loginForm: FormGroup;

  constructor(private authService: AuthService) { }

  ngOnInit() {
    this.loginForm = new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required)
    });
  }

  login(){
    this.loginRequestPayload = {
      username: this.loginForm.get('username').value,
      password: this.loginForm.get('password').value
    };

    this.authService.login(this.loginRequestPayload).subscribe(()=> {
      console.log('Login successful');
    },()=> {
      console.log('Login failed');
    });
  }

  usernameValidation(username) {
    return (!this.loginForm.get(username).valid && this.loginForm.get(username).touched);
  }

  passwordValidation(password) {
    return (!this.loginForm.get(password).valid && this.loginForm.get(password).touched);
  }
}


