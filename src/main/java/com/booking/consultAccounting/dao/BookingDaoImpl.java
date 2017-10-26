package com.booking.consultAccounting.dao;

import com.booking.consultAccounting.entity.Project;
import com.booking.consultAccounting.entity.WorkOutput;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.io.FileInputStream;
import java.util.*;

/**
 * Created by Lauri on 18.10.2017.
 */
@Repository
@Qualifier("testData")
public class BookingDaoImpl implements BookingDaoInterface {
    private SessionFactory sessionFactory;

    public BookingDaoImpl() throws Exception {
        Properties props = new Properties();
        props.load(BookingDaoImpl.class.getClassLoader().getResourceAsStream("db.properties"));
        Configuration cfg = new Configuration().configure();
        //Connection info from outer source since they are different with every user
        cfg.setProperty("hibernate.connection.url", props.getProperty("tableurl"));
        cfg.setProperty("hibernate.connection.username", props.getProperty("username"));
        cfg.setProperty("hibernate.connection.password", props.getProperty("password"));

        this.sessionFactory = cfg.buildSessionFactory();

    }

    @Transactional
    @Override
    public List<Project> getAllProjects() {
        Session session = sessionFactory.openSession();
        Query qry = session.createQuery("from Project");
        List projects = qry.list();
        session.close();
        return projects;

    }

    @Override
    public Project getProjectById(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Project p = session.load(Project.class, id);
        session.close();
        return p;
    }

    @Override
    public Project getProjectByName(String name) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Project p = session.load(Project.class, name);
        session.close();
        return p;
    }

    @Override
    public void deleteProjectById(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Project p = session.load(Project.class, id);
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

    @Override
    public void deleteWorkOutById(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(session.load(WorkOutput.class, id));
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<WorkOutput> getAllWorkOutputs(int id) {
        Session session = sessionFactory.openSession();
        Query qry = session.createQuery("from WorkOutput where project_id="+id);
        List projects = qry.list();
        session.close();
        return projects;
    }

    @Override
    public void addWorkOutput(WorkOutput work) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(work);
        session.getTransaction().commit();
        session.close();
    }
}
