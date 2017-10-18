package com.booking.consultAccounting.Entity;

/**
 * Created by Lauri on 18.10.2017.
 */
public class Project {
    private int id;
    private String name;
    private String customer;
    private float hourly_rate;
    private float charged;
    private float to_charge;
    private Phase phase;
    private boolean active;
    private static int project_id=1;

    public Project(String name, String customer, float hourly_rate, float charged, float to_charge, Phase phase, boolean active) {
        this.id = project_id;
        this.name = name;
        this.customer = customer;
        this.hourly_rate = hourly_rate;
        this.charged = charged;
        this.to_charge = to_charge;
        this.phase = phase;
        this.active = active;
        incProject_id();
    }

    public Project(){}

    private static void incProject_id() {
        project_id ++;
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

    public float getHourly_rate() {
        return hourly_rate;
    }

    public void setHourly_rate(float hourly_rate) {
        this.hourly_rate = hourly_rate;
    }

    public float getCharged() {
        return charged;
    }

    public void setCharged(float charged) {
        this.charged = charged;
    }

    public float getTo_charge() {
        return to_charge;
    }

    public void setTo_charge(float to_charge) {
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
