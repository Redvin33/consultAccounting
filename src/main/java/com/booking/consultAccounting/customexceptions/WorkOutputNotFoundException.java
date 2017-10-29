package com.booking.consultAccounting.customexceptions;

/**
 * Created by Lauri on 29.10.2017.
 */
public class WorkOutputNotFoundException extends Exception{
    public WorkOutputNotFoundException(String msg) {
        super(msg);
    }

    @Override
    public String toString() {
        return "WorkOutputNotFoundException: " + this.getMessage();
    }
}
