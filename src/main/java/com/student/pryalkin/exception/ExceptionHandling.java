package com.student.pryalkin.exception;

import com.student.pryalkin.model.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<HttpResponse> userNotFoundException(CustomerNotFoundException exception) {
        return createHttpResponse(BAD_REQUEST, exception.getMessage());
    }

    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus,
                                                     httpStatus.getReasonPhrase().toUpperCase(), message), httpStatus);
    }
}
