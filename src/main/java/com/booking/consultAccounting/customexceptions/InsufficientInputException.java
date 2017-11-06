package com.booking.consultAccounting.customexceptions;

/**
 * Created by Lauri on 6.11.2017.
 */
public class InsufficientInputException extends Exception {
    public InsufficientInputException(String msg) {
        super(msg);
    }
}
