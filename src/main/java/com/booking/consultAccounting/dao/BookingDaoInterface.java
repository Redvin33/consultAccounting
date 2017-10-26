package com.booking.consultAccounting.dao;

import com.booking.consultAccounting.entity.Project;
import com.booking.consultAccounting.entity.WorkOutput;

import java.util.Collection;
import java.util.List;

/**
 * Created by Lauri on 18.10.2017.
 */
public interface BookingDaoInterface {
    List<Project> getAllProjects();

    Project getProjectById(int id);

    Project getProjectByName(String name);

    void deleteProjectById(int id);

    void updateProject(Project i);

    void addProject(Project project);

    List<WorkOutput> getAllWorkOutputs(int id);

    void deleteWorkOutById(int id);

    void addWorkOutput(WorkOutput work);
}
