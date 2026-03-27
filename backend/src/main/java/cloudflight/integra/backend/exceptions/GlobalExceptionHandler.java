package cloudflight.integra.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException elementException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(elementException.getMessage());
    }
    @ExceptionHandler(AlreadyMemberOfThisHobbyGroupException.class)
    public ResponseEntity<String> handleAlreadyMemberOfThisHobbyGroupException(
        AlreadyMemberOfThisHobbyGroupException alreadyMemberOfThisHobbyGroupException)
    { return ResponseEntity.status(HttpStatus.CONFLICT).body(alreadyMemberOfThisHobbyGroupException.getMessage());}

    @ExceptionHandler(NotMemberOfHobbyGroupException.class)
    public ResponseEntity<String> handleNotMemberOfHobbyGroupException(
        NotMemberOfHobbyGroupException notMemberOfHobbyGroupException)
    { return ResponseEntity.status(HttpStatus.CONFLICT).body(notMemberOfHobbyGroupException.getMessage());}

}
