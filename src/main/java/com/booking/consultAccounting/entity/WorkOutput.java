package com.booking.consultAccounting.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by Lauri on 18.10.2017.
 */
public class WorkOutput implements Serializable{
    private int id;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate pvm;
    private double hours;
    private int project_id;
    private Phase paid;
    private String description;

    private static final long serialVersionUID = 13L;

    public WorkOutput(LocalDate pvm, double hours, int project, Phase paid, String description) {
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

    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate getPvm() {
        if(pvm==null){
            throw new NullPointerException("WorkOutput needs pvm attribute");
        } else {
            return pvm;
        }
    }

    public void setPvm(LocalDate pvm) {
        this.pvm = pvm;
    }

    public double getHours() {
        if(hours==Double.NaN){
            throw new NullPointerException("WorkOutput needs hours attribute");
        } else {
            return hours;
        }
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
        try {
            return paid.toString();
        } catch (Exception e) {
            throw new NullPointerException("WorkOutput needs paid attribute");
        }
    }

    public void setPaid(String paid) {
        this.paid = Phase.valueOf(paid);
    }

    public String getDescription() {
        if(description==null){
            throw new NullPointerException("WorkOutput needs hours attribute");
        } else {
            return description;
        }
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
