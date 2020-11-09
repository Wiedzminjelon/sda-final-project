import {Component, Input, OnInit} from '@angular/core';
import {PostModel} from "../models/post-model";
import {VoteService} from "../service/vote.service";
import {AuthService} from "../auth/auth.service";
import {PostService} from "../service/post.service";
import {VotePayload} from "./vote-payload";
import {VoteType} from "../models/vote-model";
import {ToastrService} from "ngx-toastr";
import {throwError} from "rxjs";

@Component({
  selector: 'app-add-vote',
  templateUrl: './add-vote.component.html',
  styleUrls: ['./add-vote.component.css']
})
export class AddVoteComponent implements OnInit {
  @Input() post: PostModel;
  votePayload: VotePayload;

  constructor(private voteService: VoteService,
              private authService: AuthService,
              private postService: PostService,
              private toastr: ToastrService) {
    this.votePayload = {
      voteType: undefined,
      postId: undefined
    }
  }

  ngOnInit(): void {
  }

  upvotePost() {
    this.votePayload.voteType = VoteType.UPVOTE;
    this.vote();
  }

  downvotePost() {
    this.votePayload.voteType = VoteType.DOWNVOTE;
    this.vote();
  }

  private vote() {
    this.votePayload.postId = this.post.id;
    this.voteService.vote(this.votePayload).subscribe(() => {
      this.updateVoteDetails();
    }, error => {
      this.toastr.error(error.error.message);
      throwError(error);
    });
  }

  private updateVoteDetails(){
    this.postService.getPost(this.post.id).subscribe(post => {
      this.post = post;
    });
  }

}
