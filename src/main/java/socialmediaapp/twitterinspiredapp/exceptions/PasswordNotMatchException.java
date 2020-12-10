package socialmediaapp.twitterinspiredapp.exceptions;

public class PasswordNotMatchException extends RuntimeException {
    public PasswordNotMatchException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
