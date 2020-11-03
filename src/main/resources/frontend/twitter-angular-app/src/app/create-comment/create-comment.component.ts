import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {CommentService} from "../service/comment.service";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-create-comment',
  templateUrl: './create-comment.component.html',
  styleUrls: ['./create-comment.component.css']
})

export class CreateCommentComponent implements OnInit {
  newCommentForm: FormGroup;
  newCommentPayload:{
    text: any;
    username: any;
    postId: any;
};

  constructor(private commentService: CommentService,
              private toastr: ToastrService) {
    this.newCommentPayload = {
      text: '',
      username:'',
      postId:''
    }
  }

  ngOnInit(): void {
    this.newCommentForm = new FormGroup({
      text: new FormControl(''),
      username: new FormControl(''),
      postId: new FormControl('')
    })
  }

  newComment() {
    this.newCommentPayload = {
      text: this.newCommentForm.get('text').value,
      username: this.newCommentForm.get('username').value,
      postId: this.newCommentForm.get('postId').value
    };

    this.commentService.newComment(this.newCommentPayload).subscribe(data => {
    }, () => {
      this.toastr.error('Adding comment failed! Please try again.')
    });
  }


}
