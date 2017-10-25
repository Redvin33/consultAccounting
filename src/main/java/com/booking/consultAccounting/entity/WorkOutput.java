package com.booking.consultAccounting.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Lauri on 18.10.2017.
 */
public class WorkOutput implements Serializable{
    private int id;
    private Date pvm;
    private double hours;
    private Project project;
    private Phase paid;
    private String description;

    private static final long serialVersionUID = 13L;

    public WorkOutput(Date pvm, float hours, Project project, Phase maksettu, String description) {
        this.pvm = pvm;
        this.hours = hours;
        this.project = project;
        this.paid = maksettu;
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
        return pvm;
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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
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
