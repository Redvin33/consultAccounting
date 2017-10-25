package com.booking.consultAccounting.entity;

import java.io.Serializable;

/**
 * Created by Lauri on 18.10.2017.
 */


public enum Phase implements Serializable{
    urakointi ("urakointi"),
    tarjous ("tarjous"),
    aloittamaton ("aloittamaton");
    private static final long serialVersionUID = 12358903454875L;
    private final String text;

    private Phase(String s) {
        text = s;
    }


    public String toString() {
        return this.text;
    }
}