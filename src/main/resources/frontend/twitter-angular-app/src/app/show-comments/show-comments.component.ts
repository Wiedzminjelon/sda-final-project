import {Component, Inject, Input, OnInit} from '@angular/core';
import {CommentService} from "../service/comment.service";
import {CommentModel} from "../models/comment-model";
import {Observable} from "rxjs";
import {PostModel} from "../models/post-model";

@Component({
  selector: 'app-show-comments',
  templateUrl: './show-comments.component.html',
  styleUrls: ['./show-comments.component.css']
})
export class ShowCommentsComponent implements OnInit {
  @Input() post: PostModel;
  comments: Observable<Array<CommentModel>>;

  constructor(private commentService: CommentService) {
  }

  ngOnInit(): void {
    this.comments = this.commentService.showComments(this.post.id);
  }

}
