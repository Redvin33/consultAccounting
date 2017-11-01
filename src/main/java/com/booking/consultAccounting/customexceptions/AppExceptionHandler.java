package com.booking.consultAccounting.customexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


/**
 * Created by Lauri on 29.10.2017.
 */
@EnableWebMvc
@ControllerAdvice
public class AppExceptionHandler{

        //Exceptionhandler for all Exceptions which should result to 404
        @ExceptionHandler(value = {ProjectNotFoundException.class, WorkOutputNotFoundException.class})
        @ResponseStatus(HttpStatus.NOT_FOUND)
        public  ResponseEntity handleProjectNotFoundException(Exception e) {
            return new ResponseEntity<Object>(new ErrorResponse(404, e),HttpStatus.NOT_FOUND);
        }

        //Exceptionhandler for all exceptions which should result to 400
        @ExceptionHandler(value = HttpMessageNotReadableException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public ResponseEntity handleBadBody(HttpMessageNotReadableException e){
            return new ResponseEntity<Object>(new ErrorResponse(400, e),HttpStatus.BAD_REQUEST);
        }
    }
