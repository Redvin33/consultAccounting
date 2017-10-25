package com.booking.consultAccounting.dao;

import com.booking.consultAccounting.entity.Project;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

/**
 * Created by Lauri on 18.10.2017.
 */
@Repository
@Qualifier("testData")
public class BookingDaoImpl implements BookingDaoInterface {
    private SessionFactory sessionFactory;

    public BookingDaoImpl() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    @Transactional
    @Override
    public List<Project> getAllProjects() {
        Session session = sessionFactory.openSession();
        Query qry = session.createQuery("from com.booking.consultAccounting.entity.Project");
        return qry.list();


    }

    @Override
    public Project getProjectById(int id) {
        return new Project();
    }

    @Override
    public void deleteProjectById(int id) {

    }

    @Override
    public void updateProject(Project p) {
        /*Project p2 = projects.get(p.getId());
        p2.setActive(p.isActive());
        p2.setCharged(p.getCharged());
        p2.setCustomer(p.getCustomer());
        p2.setHourly_rate(p.getHourly_rate());
        p2.setPhase(p.getPhase());
        p2.setName(p.getName());
        projects.put(p.getId(), p2);
        */

    }
    public void save(Project p) {

    }

    @Override
    public void addProject(Project project) {
        Session session = sessionFactory.openSession();
        session.save(project);
    }

}
