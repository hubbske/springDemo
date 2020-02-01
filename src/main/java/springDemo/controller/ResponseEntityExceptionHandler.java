package springDemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ResponseEntityExceptionHandler {

    @ExceptionHandler({NumberFormatException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<Object> handleInvalidInput(Exception e) {
        log.error(e.toString());
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}

