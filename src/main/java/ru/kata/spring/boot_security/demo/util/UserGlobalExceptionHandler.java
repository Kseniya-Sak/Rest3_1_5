package ru.kata.spring.boot_security.demo.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class UserGlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<JSONException> handleException(ResponseStatusException e) {
        JSONException dataAboutUser = new JSONException();
        dataAboutUser.setInfo(e.getMessage());
        if (e.getStatus().value() <= 451) {
            return new ResponseEntity<>(dataAboutUser, HttpStatus.BAD_REQUEST);

        }
        if (e.getStatus().value() > 451) {
            return new ResponseEntity<>(dataAboutUser, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(dataAboutUser, HttpStatus.SEE_OTHER);
    }
}
