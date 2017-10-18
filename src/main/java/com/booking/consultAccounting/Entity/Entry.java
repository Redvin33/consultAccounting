package com.booking.consultAccounting.Entity;

import java.util.Date;

/**
 * Created by Lauri on 18.10.2017.
 */
public class Entry {
    Date pvm;
    float tunnit;
    int project_id;
    Phase maksettu;
    String description;

    public Date getPvm() {
        return pvm;
    }

    public void setPvm(Date pvm) {
        this.pvm = pvm;
    }

    public float getTunnit() {
        return tunnit;
    }

    public void setTunnit(float tunnit) {
        this.tunnit = tunnit;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public Phase getMaksettu() {
        return maksettu;
    }

    public void setMaksettu(Phase maksettu) {
        this.maksettu = maksettu;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
