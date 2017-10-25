package com.booking.consultAccounting.Entity;



import org.hibernate.annotations.*;

import org.hibernate.annotations.*;
import org.hibernate.annotations.Entity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Lauri on 18.10.2017.
 */
@Entity
public class Project implements Serializable {
    @Autowired
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private int id;
    @Column(name="name")
    private String name;
    @Column(name="customer")
    private String customer;
    @Column(name="hourly_rate")
    private double hourly_rate;
    @Column(name="charged")
    private double charged;
    @Column(name="to_charge")
    private double to_charge;
    @Column(name="phase")
    @Enumerated(EnumType.STRING)
    private Phase phase;
    @Column(name="active")
    private boolean active;

    private static final long serialVersionUID = 1L;

    public Project(String name, String customer, float hourly_rate, float charged, float to_charge, Phase phase, boolean active) {
        this.name = name;
        this.customer = customer;
        this.hourly_rate = hourly_rate;
        this.charged = charged;
        this.to_charge = to_charge;
        this.phase = phase;
        this.active = active;
    }

    public Project(){}

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public double getHourly_rate() {
        return hourly_rate;
    }

    public void setHourly_rate(double hourly_rate) {
        this.hourly_rate = hourly_rate;
    }

    public double getCharged() {
        return charged;
    }

    public void setCharged(double charged) {
        this.charged = charged;
    }

    public double getTo_charge() {
        return to_charge;
    }

    public void setTo_charge(double to_charge) {
        this.to_charge = to_charge;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
