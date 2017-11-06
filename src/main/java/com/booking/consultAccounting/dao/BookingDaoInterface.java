package com.booking.consultAccounting.dao;

import com.booking.consultAccounting.customexceptions.InsufficientInputException;
import com.booking.consultAccounting.customexceptions.ProjectNotFoundException;
import com.booking.consultAccounting.customexceptions.WorkOutputNotFoundException;
import com.booking.consultAccounting.entity.Project;
import com.booking.consultAccounting.entity.WorkOutput;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

/**
 * Created by Lauri on 18.10.2017.
 */
public interface BookingDaoInterface {
    List<Project> getAllProjects() throws ProjectNotFoundException;

    Project getProjectById(int id) throws ProjectNotFoundException;

    Project getProjectByName(String name) throws ProjectNotFoundException;

    void deleteProjectById(int id) throws ProjectNotFoundException;

    void updateProject(Project i) throws ProjectNotFoundException;

    void addProject(Project project) throws InsufficientInputException;

    List<WorkOutput> getAllWorkOutputs(int id) throws WorkOutputNotFoundException;

    void deleteWorkOutById(int id) throws WorkOutputNotFoundException;

    void addWorkOutput(WorkOutput work) throws ConstraintViolationException;
}
