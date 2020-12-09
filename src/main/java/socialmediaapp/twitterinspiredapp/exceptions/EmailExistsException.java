package socialmediaapp.twitterinspiredapp.exceptions;

public class EmailExistsException extends RuntimeException {
    public EmailExistsException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
