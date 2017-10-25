package com.booking.consultAccounting.dao;

import com.booking.consultAccounting.entity.Project;

import java.util.Collection;

/**
 * Created by Lauri on 18.10.2017.
 */
public interface BookingDaoInterface {
    Collection<Project> getAllProjects();

    Project getProjectById(int id);

    void deleteProjectById(int id);

    void updateProject(Project i);

    void addProject(Project project);

}
