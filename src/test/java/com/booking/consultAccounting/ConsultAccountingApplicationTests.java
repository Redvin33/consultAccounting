package com.booking.consultAccounting;

import com.booking.consultAccounting.controller.BookingController;
import com.booking.consultAccounting.customexceptions.AppExceptionHandler;
import com.booking.consultAccounting.customexceptions.ProjectNotFoundException;
import com.booking.consultAccounting.customexceptions.WorkOutputNotFoundException;
import com.booking.consultAccounting.dao.BookingDaoImpl;
import com.booking.consultAccounting.entity.Phase;
import com.booking.consultAccounting.entity.Project;
import com.booking.consultAccounting.entity.WorkOutput;
import com.booking.consultAccounting.service.BookingService;
import org.hibernate.StaleStateException;
import org.hibernate.exception.ConstraintViolationException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class ConsultAccountingApplicationTests {

	@Rule
	public ExpectedException exceptions = ExpectedException.none();
	@Mock
	private BookingDaoImpl dao;

	private MockMvc mockMvc;

	@Test
	public void contextLoads() {
	}

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(new BookingController(new BookingService(dao)))
				  		.setControllerAdvice(new AppExceptionHandler()).build();
	}

	@Test
	public void getProjectsHttpStatus200() { //Makes GET-request to URL/projects and checks that it returns 200
		try {
			List all = new LinkedList();

			all.add(new Project("project", "company AB", 100.0,  Phase.aloittamaton, true));
			all.add(new Project("project1", "company OY", 125.7,  Phase.aloittamaton, false));

			when(dao.getAllProjects()).thenReturn(all);
			//Checks that http status code is 200 and all inputted values are correct
			this.mockMvc.perform(get("/projects")).andExpect(status().isOk()).andExpect(
					content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$[0].name").value("project"))
					.andExpect(jsonPath("$[0].customer").value("company AB"))
					.andExpect(jsonPath("$[0].hourly_rate").value(100))
					.andExpect(jsonPath("$[0].phase").value("aloittamaton"))
					.andExpect(jsonPath("$[0].active").value(true))

					.andExpect(jsonPath("$[1].name").value("project1"))
					.andExpect(jsonPath("$[1].customer").value("company OY"))
					.andExpect(jsonPath("$[1].hourly_rate").value(125.7))
					.andExpect(jsonPath("$[1].phase").value("aloittamaton"))
					.andExpect(jsonPath("$[1].active").value(false));

			verify(dao).getAllProjects();
		} catch(Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
	}

	@Test
	public void getProjectsNoResults404() throws Exception {
		when(dao.getAllProjects()).thenThrow(new ProjectNotFoundException(""));
		this.mockMvc.perform(get("/projects")).andExpect(status().isNotFound());
	}

	@Test //Test that if /projects/name/{projectname} is called with GET-request it returns 200 if no Exception is thrown
	public void getProjectByNameFound200() throws Exception {
		this.mockMvc.perform(get("/projects/name/project1")).andExpect(status().isOk());
	}

	@Test
	public void getProjectByNameNotFound404() throws Exception {
		when(dao.getProjectByName("project1")).thenThrow(new ProjectNotFoundException(""));
		this.mockMvc.perform(get("/projects/name/project1")).andExpect(status().isNotFound());
	}

	@Test //Makes GET-request to domain/projects/id/1 and checks that it returns 200 .
	public void getProjectById200() {
		try {
			this.mockMvc.perform(get("/projects/id/1")).andExpect(status().isOk());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
	}

	@Test //Makes GET-request to domain/projects/{some string} and checks that it returns 400.
	public void getProjectById400() {
		try {
			this.mockMvc.perform(get("/projects/id/string")).andExpect(status().isBadRequest());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
	}

	@Test //Makes GET-request to domain/projects/delete/1 and checks that it returns 200 .
	public void deleteProjectById200() {
		try {
			this.mockMvc.perform(get("/projects/id/1")).andExpect(status().isOk());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
	}

	@Test //Makes GET-request to domain/projects/{some string} and checks that it returns 400.
	public void deleteProjectById400() {
		try {
			this.mockMvc.perform(get("/projects/id/string")).andExpect(status().isBadRequest());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
	}
	@Test //Makes GET-request to domain/projects/{projectid that doesnt exist} and checks that it returns 404
	public void deleteNonExistingProjectById404() throws Exception {
		doThrow(new ProjectNotFoundException("")).when(dao).deleteProjectById(15);
		this.mockMvc.perform(delete("/projects/delete/15")).andExpect(status().isNotFound());

	}



	@Test //Tries to create project by making POST request to domain/projects/add and adding correctly formulated JSON
	public void createProject() {
		try {
			JSONObject new_project = new JSONObject();
			new_project.put("name", "uusi projekti");
			new_project.put("customer", "asiakas");
			new_project.put("hourly_rate", 78.0);
			new_project.put("phase", "urakointi");
			new_project.put("active", true);

			this.mockMvc.perform(post("/projects/add").content(new_project.toString()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		} catch(Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
	}

	@Test //Tries to create project by making POST request to domain/projects/add and adding correctly formulated JSON
	public void createProjectBadRequest400() {
		try {
			JSONObject new_project = new JSONObject();
			new_project.put("name", "uusi projekti");
			new_project.put("customer", "asiakas");
			new_project.put("hourly_rate", 78);
			new_project.put("phase", "väärä");
			new_project.put("active", true);

			this.mockMvc.perform(post("/projects/add").content(new_project.toString()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
		} catch(Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
	}

	@Test //Makes PUT-request to http://domain/projects/update
	public void updateExistingProject200() {
		try {
			JSONObject new_project = new JSONObject();
			new_project.put("id", 1);
			new_project.put("name", "uusi projekti");
			new_project.put("customer", "asiakas");
			new_project.put("hourly_rate", 78);
			new_project.put("charged", 1200);
			new_project.put("to_charge", 1500);
			new_project.put("phase", "urakointi");
			new_project.put("active", true);
			this.mockMvc.perform(put("/projects/update").content(new_project.toString())
					.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
	}

	@Test //Makes PUT-request to http://domain/projects/update
	public void updateExistingProject400() {
		try {
			JSONObject new_project = new JSONObject();
			new_project.put("id", 1);
			new_project.put("name", "uusi projekti");
			new_project.put("customer", "asiakas");
			new_project.put("hourly_rate", "4gjgkj");
			new_project.put("phase", "urakointi");
			new_project.put("active", true);
			this.mockMvc.perform(put("/projects/update").content(new_project.toString())
					.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test //Makes PUT-request to http://domain/projects/update
	public void updateNonExistingProject404() {
		try {
			JSONObject new_project = new JSONObject();
			new_project.put("id", 1);
			new_project.put("name", "uusi projekti");
			new_project.put("customer", "asiakas");
			new_project.put("hourly_rate", 78);
			new_project.put("charged", 1200);
			new_project.put("to_charge", 1500);
			new_project.put("phase", "urakointi");
			new_project.put("active", true);
			doThrow(new StaleStateException("")).when(dao).updateProject(any());
			this.mockMvc.perform(put("/projects/update").content(new_project.toString())
					.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	//workoutputtests

	@Test //Makes GET-request to domain/workoutputs and checks that it always returns http status code 200
	public void allWorkOutputsTest() {
		try {
			List all = new LinkedList();

			LocalDate lc = LocalDate.parse("2016-06-22");
			LocalDate lc1 = LocalDate.parse("2016-06-10");

			all.add(new WorkOutput(lc, 2.5, 3, Phase.urakointi, "did some stuff"));
			all.add(new WorkOutput(lc1, 2.5, 3, Phase.urakointi, "work"));
			when(dao.getAllWorkOutputs(1)).thenReturn(all);
			//Checks that http status code is 200 and all inputted values are correct
			this.mockMvc.perform(get("/projects/1/workoutputs")).andExpect(status().isOk()).andExpect(
					content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$[0].pvm").value("2016-06-22"))
					.andExpect(jsonPath("$[0].hours").value(2.5))
					.andExpect(jsonPath("$[0].project_id").value(3))
					.andExpect(jsonPath("$[0].paid").value("urakointi"))
					.andExpect(jsonPath("$[0].description").value("did some stuff"))

					.andExpect(jsonPath("$[1].pvm").value("2016-06-10"))
					.andExpect(jsonPath("$[1].hours").value(2.5))
					.andExpect(jsonPath("$[1].project_id").value(3))
					.andExpect(jsonPath("$[1].paid").value("urakointi"))
					.andExpect(jsonPath("$[1].description").value("work"));

		} catch(Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
	}

	@Test
	public void getWorkOutputsNoResults404() throws Exception {
		try {
			when(dao.getAllWorkOutputs(15)).thenThrow(new WorkOutputNotFoundException(""));
			this.mockMvc.perform(get("/projects/15/workoutputs")).andExpect(status().isNotFound());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test //Makes POST-request to /workoutputs with JSON-body that has all attributes that WorkOutput class constructor needs
	public void addWorkOutput() {
		try {
			JSONObject new_workOutput = new JSONObject(); //JSON object with right
			new_workOutput.put("Date", "2009-05-06");
			new_workOutput.put("hours", 2.5);
			new_workOutput.put("project_id", 1);
			new_workOutput.put("phase", "urakointi");
			new_workOutput.put("description", "suunnittelua");
			this.mockMvc.perform(post("/projects/3/workoutputs/add").content(new_workOutput.toString()).
					contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
	}

	@Test //Makes POST-request to /workoutputs with JSON-body that has all attributes that WorkOutput class constructor needs
	public void addWorkOutputBadRequest() {
		try {
			JSONObject new_workOutput = new JSONObject(); //JSON object with right
			new_workOutput.put("Date", "2009-05-06");
			new_workOutput.put("hours", "not double at all");
			new_workOutput.put("project_id", 3);
			new_workOutput.put("phase", "tarjous");
			new_workOutput.put("description", "suunnittelua");
			this.mockMvc.perform(post("/projects/3/workoutputs/add").content(new_workOutput.toString()).
					contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test //Tests that POST-request to /projects/workoutputs/{id}/add returns 404 if project cant be found with that id
	public void addWorkOutputToNonExistingProject404()  {
		try {
			JSONObject new_workOutput = new JSONObject(); //JSON object with right
			new_workOutput.put("Date", "2009-05-06");
			new_workOutput.put("hours", 2.5);
			new_workOutput.put("phase", "urakointi");
			new_workOutput.put("description", "suunnittelua");
			doThrow(new ConstraintViolationException("",new SQLException(""), "project_id"))
			.when(dao).addWorkOutput(any());
			this.mockMvc.perform(post("/projects/3/workoutputs/add").content(new_workOutput.toString())
					.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
		} catch (Exception e){
			System.out.println(e.getMessage());
			fail();
		}
	}

	@Test //Tests that DELETE-request to /projects/workoutputs/delete/{id} returns 200 when no exceptions
	public void deleteWorkOutput() {
		try {
			this.mockMvc.perform(delete("/projects/workoutputs/delete/12")).andExpect(status().isOk());
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
	}

	@Test //Tests that DELETE-request to /projects/workoutputs/delete/{id} with wrong non integer id returns 400
	public void deleteWorkOutputBadRequest400() {
		try {
			this.mockMvc.perform(delete("/projects/workoutputs/delete/notint")).andExpect(status().isBadRequest());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
	}

	@Test //Tests that DELETE-request to /projects/workoutputs/delete/{id} with non existing id returns 404
	public void deleteWorkOutputNotFound404() {
		try {
			doThrow(new WorkOutputNotFoundException("")).when(dao).deleteWorkOutById(12);
			this.mockMvc.perform(delete("/projects/workoutputs/delete/12")).andExpect(status().isNotFound());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
	}

}
