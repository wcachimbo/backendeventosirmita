package com.backend.eventos.irmita.commons;


import org.springframework.http.HttpStatus;

public class BadRequestException extends RuntimeException {
    private HttpStatus status;
    private String message;

    public BadRequestException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}