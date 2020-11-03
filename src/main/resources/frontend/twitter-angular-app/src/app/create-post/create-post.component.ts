import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";

import {Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";
import {HttpClient} from "@angular/common/http";
import {PostService} from "../service/post.service";


@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {
  newPostForm: FormGroup;
  newPostRequestPayload: {
    postName: any;
    description: any;
    username: any;
    url: any
  };


  constructor(private http: HttpClient,
              private toastr: ToastrService,
              private createPostService: PostService,
              private router: Router) {
    this.newPostRequestPayload = {
      description: '',
      url: '',
      postName: '',
      username: ''
    }
  }

  ngOnInit() {
    this.newPostForm = new FormGroup({
      description: new FormControl(''),
      url: new FormControl(''),
      postName: new FormControl(''),
      username: new FormControl('')
    })
  }

  newPost() {
    this.newPostRequestPayload = {
      description: this.newPostForm.get('description').value,
      url: this.newPostForm.get('url').value,
      postName: this.newPostForm.get('postName').value,
      username: this.newPostForm.get('username').value
    };

    this.createPostService.newPost(this.newPostRequestPayload).subscribe(data => {
    }, () => {
      this.toastr.error('Creating post failed! Please try again.')
    });

  }

  descriptionValidation(description) {
    return (!this.newPostForm.get(description).valid && this.newPostForm.get(description).touched);
  }
}




