import {VoteType} from "../models/vote-model";

export class VotePayload{
  voteType:VoteType;
  postId:number;
  userId: number;
}
