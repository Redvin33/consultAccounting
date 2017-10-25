package com.booking.consultAccounting.Service;

import com.booking.consultAccounting.Dao.BookingDaoImpl;
import com.booking.consultAccounting.Dao.BookingDaoInterface;
import com.booking.consultAccounting.Entity.Entry;
import com.booking.consultAccounting.Entity.Project;
import com.sun.prism.impl.Disposer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by Lauri on 18.10.2017.
 */
@Service
public class BookingService {
    @Autowired
    @Qualifier("testData")
    private BookingDaoInterface bookingDao;


    public Collection<Project> getAllProjects() {
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
}
