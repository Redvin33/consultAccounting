package com.booking.consultAccounting.entity;


import com.booking.consultAccounting.customexceptions.InsufficientInputException;

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
    //@Column(name="phase")
    //@Enumerated(EnumType.STRING)
    private Phase phase;
    //@Column(name="active")
    private boolean active;

    private static final long serialVersionUID = 1L;

    public Project(String name, String customer, double hourly_rate,  Phase phase, boolean active) {
        this.name = name;
        this.customer = customer;
        this.hourly_rate = hourly_rate;
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

    public String getName() throws InsufficientInputException {
        if(this.name==null){
            throw new InsufficientInputException("Project needs name attribute");
        } else {
            return this.name;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomer()  throws InsufficientInputException{
        if(customer==null){
            throw new InsufficientInputException("Project needs customer attribute.");
        } else {
            return customer;
        }
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public double getHourly_rate()  throws InsufficientInputException{
        if(hourly_rate==Double.NaN){
            throw new InsufficientInputException("Project needs hourly_rate attribute.");
        } else {
            return hourly_rate;
        }
    }

    public void setHourly_rate(double hourly_rate) {
        this.hourly_rate = hourly_rate;
    }


    public String getPhase() throws InsufficientInputException{
        try {
            return phase.toString();
        } catch (NullPointerException e) {
            throw new InsufficientInputException("Project needs proper phase attribute");
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
