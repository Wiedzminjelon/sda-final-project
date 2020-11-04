import {Component, OnInit} from '@angular/core';

import {PostModel} from "../models/post-model";
import {Observable} from "rxjs";
import {PostService} from "../service/post.service";

@Component({
  selector: 'app-post',
  templateUrl: './show-posts.component.html',
  styleUrls: ['./show-posts.component.css']
})
export class ShowPostsComponent implements OnInit {
  posts: Observable<Array<PostModel>>;

  constructor(private postService: PostService) {
  }


  ngOnInit(): void {
    this.posts = this.postService.getAllPosts();
  }

}
