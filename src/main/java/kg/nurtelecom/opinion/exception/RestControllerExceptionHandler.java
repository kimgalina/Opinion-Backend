package kg.nurtelecom.opinion.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class RestControllerExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("time", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<Map> handleHttpMessageNotReadableException(DateTimeParseException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("time", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("errors", "Введите дату рождения в правильном формате. Пример: 2001-01-21");

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map> handleNotFound(NotFoundException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("time", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND);
        body.put("errors", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("time", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("errors", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotValidException.class)
    public ResponseEntity<Map> handleNotValid(NotValidException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("time", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("errors", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoAccessException.class)
    public ResponseEntity<Map> handleNotValid(NoAccessException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("time", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("errors", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailSendingException.class)
    private ResponseEntity<Map> handleException(EmailSendingException exc) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("time", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("errors", exc.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExceedsNestingLevelException.class)
    private ResponseEntity<Map> handleException(ExceedsNestingLevelException exc) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("time", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("errors", exc.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    private ResponseEntity<Map> handleException(ExpiredJwtException exc) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("time", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("errors", exc.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(FileException.class)
    private ResponseEntity<Map> handleFileEmptyException(FileException exc) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("time", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("errors", exc.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
