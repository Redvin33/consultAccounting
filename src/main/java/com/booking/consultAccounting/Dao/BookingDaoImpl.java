package com.booking.consultAccounting.Dao;

import com.booking.consultAccounting.Entity.Phase;
import com.booking.consultAccounting.Entity.Project;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lauri on 18.10.2017.
 */
@Repository
@Qualifier("testData")
public class BookingDaoImpl implements BookingDaoInterface {
    private static Map<Integer, Project> projects;
    static {
        projects = new HashMap<Integer, Project>() {
            {
                put(1, new Project("sillanrakennus", "SITO", 65, 1200, 1500, Phase.urakointi, true));
            }
        };
    }

    @Override
    public Collection<Project> getAllProjects() {
        return this.projects.values();
    }

    @Override
    public Project getProjectById(int id) {
        return this.projects.get(id);
    }

    @Override
    public void deleteProjectById(int id) {
        this.projects.remove(id);
    }

    @Override
    public void updateProject(Project p) {
        Project p2 = projects.get(p.getId());
        p2.setActive(p.isActive());
        p2.setCharged(p.getCharged());
        p2.setCustomer(p.getCustomer());
        p2.setHourly_rate(p.getHourly_rate());
        p2.setPhase(p.getPhase());
        p2.setName(p.getName());
        projects.put(p.getId(), p2);

    }

    @Override
    public void addProject(Project project) {

    }
}
