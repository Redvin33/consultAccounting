package com.booking.consultAccounting.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Lauri on 18.10.2017.
 */
public class WorkOutput implements Serializable{
    private int id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone="GMT+3") //Without this GET-request returns date as milliseconds
    private Date pvm;
    private double hours;
    private int project_id;
    private Phase paid;
    private String description;

    private static final long serialVersionUID = 13L;

    public WorkOutput(Date pvm, double hours, int project, Phase paid, String description) {
        this.pvm = pvm;
        this.hours = hours;
        this.project_id = project;
        this.paid = paid;
        this.description = description;
    }

    public WorkOutput() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getPvm() {
        return this.pvm;
    }

    public void setPvm(Date pvm) {
        this.pvm = pvm;
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public String getPaid() {
        return this.paid.toString();
    }

    public void setPaid(String paid) {
        this.paid = Phase.valueOf(paid);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
