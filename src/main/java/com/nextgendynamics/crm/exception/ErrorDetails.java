package com.nextgendynamics.crm.exception;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
public class ErrorDetails {
    private LocalDateTime timestamp;
    private String message;
    private String details;

    public ErrorDetails(LocalDateTime timestamp, String message, String details) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }
}
