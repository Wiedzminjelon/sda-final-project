import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {CommentService} from "../service/comment.service";
import {ToastrService} from "ngx-toastr";
import {AuthService} from "../auth/auth.service";
import {PostModel} from "../models/post-model";
import {Router} from "@angular/router";

@Component({
  selector: 'app-create-comment',
  templateUrl: './create-comment.component.html',
  styleUrls: ['./create-comment.component.css']
})

export class CreateCommentComponent implements OnInit {
  @Input() post: PostModel;
  newCommentForm: FormGroup;
  newCommentPayload: {
    text: any;
    username: any;
    postId: any;
  };

  constructor(private commentService: CommentService,
              private toastr: ToastrService,
              private authService: AuthService,
              private router: Router
  ) {
    this
      .newCommentPayload = {
      text: '',
      username: '',
      postId: ''
    }
  }

  ngOnInit():
    void {
    this.newCommentForm = new FormGroup({
      text: new FormControl(''),
      username: new FormControl(''),
      postId: new FormControl('')
    })
  }

  newComment() {
    this.newCommentPayload = {
      text: this.newCommentForm.get('text').value,
      username: this.authService.getUserName(),
      postId: this.post.id
    };

    this.commentService.newComment(this.newCommentPayload).subscribe(data => {
      window.location.reload()
    }, () => {
      this.toastr.error('Adding comment failed! Please try again.')
    });
  }


}
