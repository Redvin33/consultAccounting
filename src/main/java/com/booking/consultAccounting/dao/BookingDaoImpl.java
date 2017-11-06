package com.booking.consultAccounting.dao;

import com.booking.consultAccounting.customexceptions.ProjectNotFoundException;
import com.booking.consultAccounting.customexceptions.WorkOutputNotFoundException;
import com.booking.consultAccounting.entity.Project;
import com.booking.consultAccounting.entity.WorkOutput;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StaleStateException;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Properties;

/**
 * Created by Lauri on 18.10.2017.
 */
@Repository
@Qualifier("testData")
public class BookingDaoImpl implements BookingDaoInterface {
    private SessionFactory sessionFactory;

    public BookingDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

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


    @Override
    public List<Project> getAllProjects() throws ProjectNotFoundException {
        Session session = sessionFactory.openSession();
        Query qry = session.createQuery("from Project");
        List projects = qry.list();
        if(qry.list().size() == 0) {
            throw new ProjectNotFoundException("Couldn't find any projects from database");
        }
        session.close();
        return projects;
    }

    @Override
    public Project getProjectById(int id) throws ProjectNotFoundException {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Project p = session.get(Project.class, id);
        session.close();
        if(p == null) { //If p is null t
            throw new ProjectNotFoundException("Cant find project with id "+ id);
        }
        return p;
    }

    @Override
    public Project getProjectByName(String name) throws ProjectNotFoundException{
        Session session = sessionFactory.openSession();
        Query qry = session.createQuery("from Project where name='" + name + "'");
        qry.setMaxResults(1);
        if (qry.list().size() == 0) {
            throw new ProjectNotFoundException("Cant find project with name " + name);
        }
        Project p = (Project) qry.list().get(0);
        session.close();
        return p;
    }

    private void deleteProjectsWorkOutputs(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query qry = session.createQuery("from WorkOutput where project_id="+id);
        List<WorkOutput> related_works = qry.list();
        String hql = "delete from WorkOutput where project_id= :id"; //We delete first projects workoutputs so we dont violate foreign key constraint
        session.createQuery(hql).setLong("id", id).executeUpdate();
        session.getTransaction().commit();
        session.close();
        return;

    }

    @Override
    public void deleteProjectById(int id) throws ProjectNotFoundException{
        deleteProjectsWorkOutputs(id);
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Project p = session.get(Project.class, id);
        if(p == null) {     //If p == null it means that no project with requested id exists in database.
            session.close();
            throw new ProjectNotFoundException("Cant find project with id " +id);
        }
        session.delete(p);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void updateProject(Project p) throws StaleStateException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(p);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void addProject(Project project) throws JpaSystemException{
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(project);
        session.getTransaction().commit();
        session.close();

    }

    @Override
    public void deleteWorkOutById(int id) throws WorkOutputNotFoundException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        WorkOutput w = session.load(WorkOutput.class, id);
        if (w==null) {
            throw new WorkOutputNotFoundException("Cant find workoutput with id "+ id);
        }
        session.delete(w);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<WorkOutput> getAllWorkOutputs(int id) throws WorkOutputNotFoundException {
        Session session = sessionFactory.openSession();
        Query qry = session.createQuery("from WorkOutput where project_id="+id);
        List workoutputs = qry.list();
        session.close();
        if (workoutputs.size() == 0) {
            throw new WorkOutputNotFoundException("No workoutputs found with project_id "+id+".");
        }
        return workoutputs;
    }

    @Override
    public void addWorkOutput(WorkOutput work) throws ConstraintViolationException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(work);
        session.getTransaction().commit();
        session.close();
    }
}
