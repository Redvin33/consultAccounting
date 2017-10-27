package com.booking.consultAccounting.controller;

import com.booking.consultAccounting.entity.Project;
import com.booking.consultAccounting.entity.WorkOutput;
import com.booking.consultAccounting.service.BookingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

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

    //Returns all projects
    @RequestMapping(method = RequestMethod.GET)
    public Collection<Project> getAllProjects(){
        return bookingService.getAllProjects();
    }

    //with GET request to http://domain/projects/{id} returns project that matches id.
    @RequestMapping(value= "/id/{id}", method = RequestMethod.GET)
    public Project getProjectById(@PathVariable("id") int id){
        return bookingService.getProjectById(id);
    }

    //with GET-request to http://domain/projects/name={name} returns project that matches name
    @RequestMapping(value= "/name/{name}", method = RequestMethod.GET)
    public ResponseEntity<Project> getProjectByName(@PathVariable("name") String name){

        try {
            Project p = bookingService.getProjectByName(name);
            return new ResponseEntity<>(p, HttpStatus.OK);
        } catch(IndexOutOfBoundsException e) {
            System.out.println("cathcattiin");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //with DELETE request to http://domain/projects/delete/{id} deletes project that matches id
    @RequestMapping(value="/delete/{id}", method = RequestMethod.DELETE)
    public void deleteProjectById(@PathVariable("id") int id) {
        bookingService.deleteProjectById(id);
    }

    //with PUT request with JSON content to http://domain/projects/id edits existing entry in entryData
    @RequestMapping(value="/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateProject(@RequestBody Project project) {
        bookingService.updateProject(project);
    }

    //with POST
    @RequestMapping(value="/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addProject(@RequestBody Project project) {
        bookingService.addProject(project);
    }

    @RequestMapping(value ="/{id}/workoutputs", method =RequestMethod.GET)
    public List<WorkOutput> getAllWorkOutputs(@PathVariable("id") int id) {
        return bookingService.getAllWorkOutputs(id);
    }

    //with POST
    @RequestMapping(value="/{id}/workoutputs/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addWorkOutput(@RequestBody WorkOutput work, @PathVariable int id) {
        work.setProject_id(id);
        bookingService.addWorkOutput(work);
    }

    //with DELETE request to http://domain/projects/workoutputs/{id} deletes workoutput that matches id
    @RequestMapping(value="/workoutputs/delete/{id}", method = RequestMethod.DELETE)
    public void deleteWorkOutputById(@PathVariable("id") int id) {
        bookingService.deleteWorkOutputById(id);
    }

}
