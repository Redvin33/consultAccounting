package com.booking.consultAccounting.customexceptions;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by Lauri on 29.10.2017.
 */
public class ErrorResponse {
    private int statuscode;
    private String exception;
    private String message;
    private String stacktrace;

    public ErrorResponse(int statuscode, Exception e) {
        this.statuscode = statuscode;
        this.message = e.getMessage();
        this.exception = e.getClass().getSimpleName();
        StringWriter s = new StringWriter();
        PrintWriter p = new PrintWriter(s);
        e.printStackTrace(p);
        this.stacktrace = s.toString();
    }

    public int getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(int statuscode) {
        this.statuscode = statuscode;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStacktrace() {
        return stacktrace;
    }

    public void setStacktrace(String stacktrace) {
        this.stacktrace = stacktrace;
    }
}
