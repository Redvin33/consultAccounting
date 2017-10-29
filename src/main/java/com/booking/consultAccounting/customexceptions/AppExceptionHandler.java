package com.booking.consultAccounting.customexceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static java.lang.System.in;


/**
 * Created by Lauri on 29.10.2017.
 */
@EnableWebMvc
@ControllerAdvice
public class AppExceptionHandler{

        //Exceptionhandler for all Exceptions which should result to 404
        @ExceptionHandler(value = ProjectNotFoundException.class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        public  ResponseEntity handleProjectNotFoundException(ProjectNotFoundException e) {
            return new ResponseEntity<Object>(e.toString(),HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(value = HttpMessageNotReadableException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public ResponseEntity handleBadBody(HttpMessageNotReadableException e){
            return new ResponseEntity<Object>(e.toString(),HttpStatus.NOT_FOUND);
        }
    }
