package com.booking.consultAccounting.customexceptions;

/**
 * Created by Lauri on 6.11.2017.
 * Exception class for missing attribute in PUT or POST request
 */
public class InsufficientInputException extends Exception {
    public InsufficientInputException(String msg) {
        super(msg);
    }
}
