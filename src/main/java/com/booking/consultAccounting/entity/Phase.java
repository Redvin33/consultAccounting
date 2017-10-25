package com.booking.consultAccounting.entity;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Lauri on 18.10.2017.
 */


public enum Phase implements Serializable{
    urakointi ("urakointi"),
    tarjous ("tarjous"),
    aloittamaton ("aloittamaton");
    //private static final long serialVersionUID = 12358903454875L;
    private final String text;

    private Phase(String s) {
        text = s;
    }

    public String toString() {
        return this.text;
    }

}