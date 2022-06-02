package com.element.bookingapplication.core.exception;

import org.springframework.http.HttpStatus;

public record ExceptionResponse(HttpStatus httpStatus, String message) {
}
