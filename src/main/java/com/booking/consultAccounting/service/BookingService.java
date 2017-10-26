package com.booking.consultAccounting.service;

import com.booking.consultAccounting.dao.BookingDaoInterface;
import com.booking.consultAccounting.entity.Project;
import com.booking.consultAccounting.entity.WorkOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * Created by Lauri on 18.10.2017.
 */
//This class works as an extra abstraction layer. It could contain some extra logic which would look bad on on controller layer.
// Now it just transmits requests to bookingDao object
@Service
public class BookingService {
    @Autowired
    @Qualifier("testData")
    private BookingDaoInterface bookingDao;


    public List<Project> getAllProjects() {
        return this.bookingDao.getAllProjects();
    }

    public Project getProjectById(int i) {
        return this.bookingDao.getProjectById(i);
    }

    public void updateProject(Project project) {
        this.bookingDao.updateProject(project);
    }

    public void deleteProjectById(int id) {
        this.bookingDao.deleteProjectById(id);
    }

    public void addProject(Project project) {
        this.bookingDao.addProject(project);
    }

    public List<WorkOutput> getAllWorkOutputs(int id) {
        return this.bookingDao.getAllWorkOutputs(id);
    }

    public Project getProjectByName(String name) {
        return this.bookingDao.getProjectByName(name);
    }

    public void deleteWorkOutputById(int id) {
        this.bookingDao.deleteWorkOutById(id);
    }

    public void addWorkOutput(WorkOutput work) {
        this.bookingDao.addWorkOutput(work);
    }
}
