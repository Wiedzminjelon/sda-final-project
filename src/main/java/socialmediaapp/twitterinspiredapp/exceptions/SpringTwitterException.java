package socialmediaapp.twitterinspiredapp.exceptions;

public class SpringTwitterException extends RuntimeException {
    public SpringTwitterException(String exceptionMessage) {
        super((exceptionMessage));
    }
}
