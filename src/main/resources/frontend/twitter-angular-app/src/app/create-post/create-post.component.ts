import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";

import {CreatePostService} from "./createPost.service";


@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {
  newPostRequestPayload: { postName: any; id: any; description: any; userName: any; url: any };
  newPostForm: FormGroup;

  constructor( private createPostService: CreatePostService ) {
    this.newPostRequestPayload = {
      description: '',
      id: 0,
      url: '',
      postName: '',
      userName: ''
    }

  }

  ngOnInit(){
    this.newPostForm = new FormGroup({
      description: new FormControl(''),
      url: new FormControl(''),
      postName: new FormControl(''),
      userName: new FormControl('')
    })
  }

  newPost() {
    this.newPostRequestPayload = {
      description: this.newPostForm.get('description').value,
      id: 0,
      url: this.newPostForm.get('url').value,
      postName: this.newPostForm.get('postName').value,
      userName: this.newPostForm.get('userName').value


    }
    }
  }




