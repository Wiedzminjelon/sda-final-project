import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {SignupRequestPayload} from "./signup-request.payload";
import {AuthService} from "../auth.service";
import {Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {
  signupRequestPayload: SignupRequestPayload;
  signupForm: FormGroup;

  constructor(private authService: AuthService, private router: Router, private toastr: ToastrService) {
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

    this.authService.signup(this.signupRequestPayload)
      .subscribe(() => {
      this.router.navigate(['/login'], {queryParams: {registered: 'true'}});
    }, () => {
      this.toastr.error('Registration Failed! Please try again.')
    });
  }

  emailValidation(email) {
    return (!this.signupForm.get(email).valid && this.signupForm.get(email).touched);
  }

  passwordValidation(password) {
    return (!this.signupForm.get(password).valid && this.signupForm.get(password).touched);
  }

  usernameValidation(username) {
    return (!this.signupForm.get(username).valid && this.signupForm.get(username).touched);
  }

  passwordRValidation(passwordR) {
    return (!this.signupForm.get(passwordR).valid && this.signupForm.get(passwordR).touched);
  }
}
