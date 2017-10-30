package com.booking.consultAccounting.customexceptions;

import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

/**
 * Created by Lauri on 29.10.2017.
 */
public class ProjectNotFoundException extends Exception {
    public ProjectNotFoundException(String msg) {
        super(msg);
    }
}
