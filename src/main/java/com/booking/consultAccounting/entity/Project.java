package com.booking.consultAccounting.entity;


import java.io.Serializable;

/**
 * Created by Lauri on 18.10.2017.
 */
//@Entity
public class Project implements Serializable {

    private int id;
    //@Column(name="name")
    private String name;
    //@Column(name="customer")
    private String customer;
    //@Column(name="hourly_rate")
    private double hourly_rate;
    //@Column(name="charged")
    private double charged;
    //@Column(name="to_charge")acti
    private double to_charge;
    //@Column(name="phase")
    //@Enumerated(EnumType.STRING)
    private Phase phase;
    //@Column(name="active")
    private boolean active;

    private static final long serialVersionUID = 1L;

    public Project(String name, String customer, double hourly_rate, double charged, double to_charge, Phase phase, boolean active) {
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
        if(this.name==null){
            throw new NullPointerException("");
        } else {
            return this.name;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomer() {
        if(customer==null){
            throw new NullPointerException("Project needs customer attribute.");
        } else {
            return customer;
        }
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public double getHourly_rate() {
        if(hourly_rate==Double.NaN){
            throw new NullPointerException("Project needs hourly_rate attribute.");
        } else {
            return hourly_rate;
        }
    }

    public void setHourly_rate(double hourly_rate) {
        this.hourly_rate = hourly_rate;
    }

    public double getCharged() {
        if(charged==Double.NaN){
            throw new NullPointerException("Project needs charged attribute.");
        } else {
            return charged;
        }
    }

    public void setCharged(double charged) {
        this.charged = charged;
    }

    public double getTo_charge() {
        if(to_charge==Double.NaN){
            throw new NullPointerException("Project needs hourly_rate attribute.");
        } else {
            return to_charge;
        }
    }

    public void setTo_charge(double to_charge) {
        this.to_charge = to_charge;
    }

    public String getPhase() {
        try {
            return phase.toString();
        } catch (Exception e) {
            throw new NullPointerException("Project needs phase attribute");
        }


    }
    //Using string since
    public void setPhase(String phase) throws Exception {
        this.phase = Phase.valueOf(phase);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


}
