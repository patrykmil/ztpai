package iu.iu.spring_app.api.errors;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex) {
        ExceptionResponse error = new ExceptionResponse(
                ex.getMessage(),
                500
        );
        return ResponseEntity.internalServerError().body(error);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleRuntimeException(RuntimeException ex) {
        ExceptionResponse error = new ExceptionResponse(
                ex.getMessage(),
                400
        );
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(ResourceNotFoundException ex) {
        ExceptionResponse error = new ExceptionResponse(
                ex.getMessage(),
                404
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionResponse> handleAccessDeniedException(AccessDeniedException ex) {
        ExceptionResponse error = new ExceptionResponse(
                ex.getMessage(),
                403
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ExceptionResponse> handleExpiredJwtException(ExpiredJwtException ex) {
        ExceptionResponse error = new ExceptionResponse(
                "Token expired",
                401
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

}