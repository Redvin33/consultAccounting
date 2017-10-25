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
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Project p = session.get(Project.class, id);
        session.close();
        return p;
    }

    @Override
    public void deleteProjectById(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Project p = session.get(Project.class, id);
        session.delete(p);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void updateProject(Project p) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(p);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void addProject(Project project) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(project);
        session.getTransaction().commit();
        session.close();
    }
}
