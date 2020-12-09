package socialmediaapp.twitterinspiredapp.exceptions;

public class UsernameExistsException extends RuntimeException {
    public UsernameExistsException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
