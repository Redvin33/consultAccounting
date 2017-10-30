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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xpath.internal.operations.Gt;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.postgresql.util.GT;
import org.postgresql.util.PSQLException;
import org.postgresql.util.ServerErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.xml.ws.Response;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static com.sun.org.apache.xerces.internal.util.PropertyState.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class ConsultAccountingApplicationTests {

	@Rule
	public ExpectedException exceptions = ExpectedException.none();
	@Mock
	private BookingDaoImpl dao;
	@Mock
	private BookingService service;

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

			all.add(new Project("project", "company AB", 100.0, 1345,
					3456, Phase.aloittamaton, true));
			all.add(new Project("project1", "company OY", 100.0, 1345,
					3456, Phase.aloittamaton, false));

			when(dao.getAllProjects()).thenReturn(all);
			//Checks that http status code is 200 and all inputted values are correct
			this.mockMvc.perform(get("/projects")).andExpect(status().isOk()).andExpect(
					content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$[0].name").value("project"))
					.andExpect(jsonPath("$[0].customer").value("company AB"))
					.andExpect(jsonPath("$[0].hourly_rate").value(100))
					.andExpect(jsonPath("$[0].charged").value(1345))
					.andExpect(jsonPath("$[0].to_charge").value(3456))
					.andExpect(jsonPath("$[0].phase").value("aloittamaton"))
					.andExpect(jsonPath("$[0].active").value(true))

					.andExpect(jsonPath("$[1].name").value("project1"))
					.andExpect(jsonPath("$[1].customer").value("company OY"))
					.andExpect(jsonPath("$[1].hourly_rate").value(100))
					.andExpect(jsonPath("$[1].charged").value(1345))
					.andExpect(jsonPath("$[1].to_charge").value(3456))
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


	/*
	@Test
	public void testExceptionIfNoDataFound() throws Exception{
		try {
			List all = new LinkedList();
			exception.expect(ProjectNotFoundException.class);
			//return mocked result set on find
			when(dao.getAllProjects()).thenReturn(all);
			//call the main method you want to test
			Response r = service.getAllProjects();
			//verify the method was called
			verify(dao).getAllProjects();

		} catch (Exception e) {

		}

	}
	*/
	@Test //Makes GET-request to domain/projects/{some string} and checks that it returns 400.
	public void getProjectById() {
		try {
			this.mockMvc.perform(get("/projects/id/string")).andExpect(status().isBadRequest());
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
	@Test //Makes GET-request to domain/projects/{projectid that doesnt exist} and checks that it returns 404
	public void getNonExistingProjectById404() throws Exception {
		when(dao.getProjectById(15)).thenThrow(new ProjectNotFoundException(""));
		this.mockMvc.perform(get("/projects/id/15")).andExpect(status().isNotFound());
	}



	@Test //Tries to create project by making POST request to domain/projects/add and adding correctly formulated JSON
	public void createProject() {
		try {
			JSONObject new_project = new JSONObject();
			new_project.put("name", "uusi projekti");
			new_project.put("customer", "asiakas");
			new_project.put("hourly_rate", 78);
			new_project.put("charged", 1200);
			new_project.put("to_charge", 1500);
			new_project.put("phase", "urakointi");
			new_project.put("active", true);

			this.mockMvc.perform(post("/projects/add").content(new_project.toString()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		} catch(Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
	}




	//workoutputtests


	@Test //Makes GET-request to domain/workoutputs and checks that it always returns http status code 200
	public void allWorkOutputsTest() {
		try {
			List all = new LinkedList();
			SimpleDateFormat sdf = new SimpleDateFormat(("dd-mm-yyyy"));

			all.add(new WorkOutput(sdf.parse("30-10-2017"), 2.5, 3, Phase.urakointi, "did some stuff"));
			all.add(new WorkOutput(sdf.parse("31-10-2017"), 2.5, 3, Phase.urakointi, "work"));
			when(dao.getAllProjects()).thenReturn(all);
			//Checks that http status code is 200 and all inputted values are correct
			this.mockMvc.perform(get("/projects")).andExpect(status().isOk()).andExpect(
					content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
					.andExpect(jsonPath("$[0].pvm").value("2017-10-30"))
					.andExpect(jsonPath("$[0].hours").value(2.5))
					.andExpect(jsonPath("$[0].project_id").value(3))
					.andExpect(jsonPath("$[0].paid").value("urakointi"))
					.andExpect(jsonPath("$[0].description").value("did some stuff"))

					.andExpect(jsonPath("$[1].pvm").value("2017-10-31"))
					.andExpect(jsonPath("$[1].hours").value(2.5))
					.andExpect(jsonPath("$[1].project_id").value(3))
					.andExpect(jsonPath("$[1].paid").value("urakointi"))
					.andExpect(jsonPath("$[1].description").value("work"));


			verify(dao).getAllProjects();
		} catch(Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
	}



	@Test //Makes GET-request to domain/workoutputs/9999999999999 and checks that it returns http status code 404
	public void nonExistingProjectWorkOutputs() {

	}

	@Test //Makes POST-request to domain/workoutputs with JSON-body that has all attributes that WorkOutput class constructor needs
	public void addWorkOutput() throws Exception {
		JSONObject new_workOutput = new JSONObject(); //JSON object with right
		new_workOutput.put("Date", "2009-05-06");
		new_workOutput.put("hours", 2.5);
		new_workOutput.put("project_id", 1);
		new_workOutput.put("phase", "urakointi");
		new_workOutput.put("description", "suunnittelua");
		this.mockMvc.perform(get("/projects/3/workoutputs/add")).andExpect(status().isOk());
	}

	@Test //Tests that POST-request to /projects/workoutputs/{id}/add returns 404 if project cant be found with that id
	public void addWorkOutputToNonExistingProject404() throws Exception {
		JSONObject new_workOutput = new JSONObject(); //JSON object with right
		new_workOutput.put("Date", "2009-05-06");
		new_workOutput.put("hours", 2.5);
		new_workOutput.put("project_id", 1);
		new_workOutput.put("phase", "urakointi");
		new_workOutput.put("description", "suunnittelua");
		doThrow(new PSQLException(new ServerErrorMessage("", 1)))
				.when(dao).addWorkOutput(any());
		this.mockMvc.perform(post("/projects/3/workoutputs/add").content(new_workOutput.toString())
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}

}
