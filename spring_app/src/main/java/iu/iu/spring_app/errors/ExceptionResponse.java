package iu.iu.spring_app.errors;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ExceptionResponse {
    // Getters and setters
    private final LocalDateTime timestamp;
    private final String message;
    private final int status;

    public ExceptionResponse(String message, int status) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.status = status;
    }

}