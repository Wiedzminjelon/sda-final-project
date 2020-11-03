import {Component, OnInit} from '@angular/core';

import {PostModel} from "../models/post-model";
import {Observable} from "rxjs";
import {PostService} from "../service/post.service";

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {
  post: Observable<Array<PostModel>>;

  constructor(private postService: PostService) {
  }


  ngOnInit(): void {
    this.post = this.postService.getAllPosts();
  }

}
