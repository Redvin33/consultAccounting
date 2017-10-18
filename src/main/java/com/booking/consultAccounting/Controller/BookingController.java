package com.booking.consultAccounting.Controller;

import com.booking.consultAccounting.Entity.Project;
import com.booking.consultAccounting.Service.BookingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import javax.validation.constraints.Null;
import java.util.Collection;

/**
 * Created by Lauri on 18.10.2017.
 */
@RestController
@RequestMapping("/projects")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    //Returns all entrys
    @RequestMapping(method = RequestMethod.GET)
    public Collection<Project> getAllentrys(){
        return bookingService.getAllProjects();
    }

    //with GET request to http://domain/entrys/{id} returns entry that matches id.
    @RequestMapping(value= "/{id}", method = RequestMethod.GET)
    public Project getProjectById(@PathVariable("id") int id) throws Exception{
        Project project = bookingService.getProjectById(id);
        if (project == null) {
            throw new NoSuchRequestHandlingMethodException("show", Project.class);

        }else {
            return bookingService.getProjectById(id);
        }
    }

    //with DELETE request to http://domain/entrys/{id} deletes entry that matches id
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public void deleteentryById(@PathVariable("id") int id) {
        bookingService.deleteProjectById(id);
    }

    //with PUT request with JSON content to http://domain/entrys edits existing entry in entryData
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateProject(@RequestBody Project project) {
        bookingService.updateProject(project);
    }

    //with POSY
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void insertentry(@RequestBody Project project) {
        bookingService.addProject(project);
    }
}
