package com.booking.consultAccounting.service;

import com.booking.consultAccounting.customexceptions.ProjectNotFoundException;
import com.booking.consultAccounting.customexceptions.WorkOutputNotFoundException;
import com.booking.consultAccounting.dao.BookingDaoInterface;
import com.booking.consultAccounting.entity.Project;
import com.booking.consultAccounting.entity.WorkOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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


    public List<Project> getAllProjects() throws ProjectNotFoundException{
        return this.bookingDao.getAllProjects();
    }

    public Project getProjectById(int i) throws ProjectNotFoundException {
        return this.bookingDao.getProjectById(i);
    }

    public Project getProjectByName(String name) throws ProjectNotFoundException{
        return this.bookingDao.getProjectByName(name);

    }

    public void updateProject(Project project) throws ProjectNotFoundException{
        this.bookingDao.updateProject(project);
    }

    public void deleteProjectById(int id) throws ProjectNotFoundException {
        this.bookingDao.deleteProjectById(id);
    }

    public void addProject(Project project){
        this.bookingDao.addProject(project);
    }

    public List<WorkOutput> getAllWorkOutputs(int id) throws WorkOutputNotFoundException {
        return this.bookingDao.getAllWorkOutputs(id);
    }

    public void deleteWorkOutputById(int id)throws WorkOutputNotFoundException {
        this.bookingDao.deleteWorkOutById(id);
    }

    public void addWorkOutput(WorkOutput work) {
        this.bookingDao.addWorkOutput(work);
    }
}
