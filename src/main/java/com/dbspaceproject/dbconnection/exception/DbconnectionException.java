package com.dbspaceproject.dbconnection.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class DbconnectionException extends RuntimeException {
    public HttpStatus status;
    public String message;

    public DbconnectionException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }
}
