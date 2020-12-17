package socialmediaapp.twitterinspiredapp.validator;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import socialmediaapp.twitterinspiredapp.annotation.PasswordMatches;
import socialmediaapp.twitterinspiredapp.dto.RegisterRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        try {
            RegisterRequest registerRequest = (RegisterRequest) obj;
            return registerRequest.getPassword().equals(registerRequest.getConfirmedPassword());
        } catch (ResponseStatusException responseStatusException){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Both passwords has to be identical");
        }

    }
}
