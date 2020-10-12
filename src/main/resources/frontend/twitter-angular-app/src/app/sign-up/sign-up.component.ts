import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {SignupRequestPayload} from "./signup-request.payload";
import {AuthService} from "../auth/auth.service";

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {
  signupRequestPayload: SignupRequestPayload;
  signupForm: FormGroup;


  constructor(private authService: AuthService) {
  }


  ngOnInit() {
    this.signupForm = new FormGroup({
      email: new FormControl('', [Validators.required, Validators.email]),
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
      passwordR: new FormControl('', Validators.required)


    });
  }

  signup() {
    this.signupRequestPayload = {
      username: this.signupForm.get('username').value,
      email: this.signupForm.get('email').value,
      password: this.signupForm.get('password').value,
      confirmedPassword: this.signupForm.get('passwordR').value
    };

    this.authService.signup(this.signupRequestPayload).subscribe(() => {
      console.log('Signup successful');
    }, () => {
      console.log('Signup failed');
    });

  }

  passwordValidation(password) {
    if (!this.signupForm.get(password).valid && this.signupForm.get(password).touched) {

    }
  }
}
