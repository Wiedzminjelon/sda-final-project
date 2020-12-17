package socialmediaapp.twitterinspiredapp.enums;

public enum VOTE_TYPE {
    UPVOTE(1),
    DOWNVOTE(-1);

    private int direction;
    
    VOTE_TYPE(int direction){}

}
