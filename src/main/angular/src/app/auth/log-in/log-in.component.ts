import {Component, OnInit} from '@angular/core';
import {LogInRequestPayload} from "./log-in-request.payload";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-log-in',
  templateUrl: './log-in.component.html',
  styleUrls: ['./log-in.component.css']
})

export class LogInComponent implements OnInit {
  loginRequestPayload: LogInRequestPayload;
  loginForm: FormGroup;
  isError: boolean;
  registerSuccessMessage: string;

  constructor(private authService: AuthService,
              private activatedRoute: ActivatedRoute,
              private router: Router,
              private toastr: ToastrService) {
    this.loginRequestPayload = {
      username: '',
      password: ''
    };
  }

  ngOnInit() {
    this.loginForm = new FormGroup({
      username: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required)
    });

    this.activatedRoute.queryParams
      .subscribe(params => {
        if (params.registered !== undefined && params.registered === 'true') {
          this.toastr.success('Signup successful!');
          this.registerSuccessMessage = 'Please check your inbox for activation link ' +
            'activate your account before you Login!';
        }
      });
  }

  login() {
    this.loginRequestPayload = {
      username: this.loginForm.get('username').value,
      password: this.loginForm.get('password').value,
    };

    this.authService.login(this.loginRequestPayload).subscribe(data => {
      if (data) {
        this.isError = false;
        this.router.navigateByUrl("/")
        this.toastr.success('Login Successful')
      } else {
        this.isError = true;
      }
    });
  }

  usernameValidation(username) {
    return (!this.loginForm.get(username).valid && this.loginForm.get(username).touched);
  }

  passwordValidation(password) {
    return (!this.loginForm.get(password).valid && this.loginForm.get(password).touched);
  }
}

