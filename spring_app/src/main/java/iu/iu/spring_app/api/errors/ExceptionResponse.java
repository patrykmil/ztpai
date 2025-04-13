package iu.iu.spring_app.api.errors;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ExceptionResponse {
    private final LocalDateTime timestamp;
    private final String message;
    private final int status;

    public ExceptionResponse(String message, int status) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.status = status;
    }

}