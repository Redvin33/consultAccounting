package com.booking.consultAccounting.controller;

import com.booking.consultAccounting.customexceptions.ProjectNotFoundException;
import com.booking.consultAccounting.customexceptions.WorkOutputNotFoundException;
import com.booking.consultAccounting.entity.Project;
import com.booking.consultAccounting.entity.WorkOutput;
import com.booking.consultAccounting.service.BookingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * Created by Lauri on 18.10.2017.
 */
@RestController
@RequestMapping("/projects")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    //Returns all projects
    @RequestMapping(method = RequestMethod.GET)
    public List<Project> getAllProjects() throws Exception {
        return bookingService.getAllProjects();
    }

    //with GET request to http://domain/projects/{id} returns project that matches id.
    @RequestMapping(value= "/id/{id}", method = RequestMethod.GET)
    public Project getProjectById(@PathVariable("id") int id) throws ProjectNotFoundException {
        return bookingService.getProjectById(id);
    }

    //with GET-request to http://domain/projects/name={name} returns project that matches name
    @RequestMapping(value= "/name/{name}", method = RequestMethod.GET)
    public ResponseEntity<Project> getProjectByName(@PathVariable("name") String name) throws ProjectNotFoundException{
        Project p = bookingService.getProjectByName(name);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }

    //with POST
    @RequestMapping(value="/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addProject(@ModelAttribute Project project) {
        bookingService.addProject(project);
    }

    //with DELETE request to http://domain/projects/delete/{id} deletes project that matches id
    @RequestMapping(value="/delete/{id}", method = RequestMethod.DELETE)
    public void deleteProjectById(@PathVariable("id") int id) throws Exception {
        bookingService.deleteProjectById(id);
    }

    //with PUT request with JSON content to http://domain/projects/id edits existing entry in entryData
    @RequestMapping(value="/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateProject(@RequestBody Project project) throws Exception {
        bookingService.updateProject(project);
    }
    
    @RequestMapping(value ="/{id}/workoutputs", method =RequestMethod.GET)
    public List<WorkOutput> getAllWorkOutputs(@PathVariable("id") int id) throws WorkOutputNotFoundException{
        return bookingService.getAllWorkOutputs(id);
    }

    //with POST
    @RequestMapping(value="/{id}/workoutputs/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addWorkOutput(@RequestBody WorkOutput work, @PathVariable int id) throws ProjectNotFoundException {
        work.setProject_id(id);
        bookingService.addWorkOutput(work);
    }

    //with DELETE request to http://domain/projects/workoutputs/{id} deletes workoutput that matches id
    @RequestMapping(value="/workoutputs/delete/{id}", method = RequestMethod.DELETE)
    public void deleteWorkOutputById(@PathVariable("id") int id) throws WorkOutputNotFoundException {
        bookingService.deleteWorkOutputById(id);
    }

    public BookingService getBookingService() {
        return bookingService;
    }

    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }
}
