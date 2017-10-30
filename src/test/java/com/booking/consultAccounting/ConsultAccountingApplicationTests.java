package com.booking.consultAccounting;

import com.booking.consultAccounting.controller.BookingController;
import com.booking.consultAccounting.customexceptions.AppExceptionHandler;
import com.booking.consultAccounting.customexceptions.ProjectNotFoundException;
import com.booking.consultAccounting.dao.BookingDaoImpl;
import com.booking.consultAccounting.entity.Phase;
import com.booking.consultAccounting.entity.Project;
import com.booking.consultAccounting.entity.WorkOutput;
import com.booking.consultAccounting.service.BookingService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
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
	public void getAllProjectsHttpStatus200() { //Makes GET-request to URL/projects and checks that it returns 200
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
	public void getAllProjectsWhenNoProjectsFound404() throws Exception {
		when(dao.getAllProjects()).thenThrow(new ProjectNotFoundException(""));
		this.mockMvc.perform(get("/projects")).andExpect(status().isNotFound());
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

	@Test //Makes GET-request to domain/projects/{projectid that doesnt exist} and checks that it returns 404
	public void getNonExistingProjectByIdShouldReturnHttpStatusCode404() throws Exception {
		when(dao.getProjectById(15)).thenThrow(new ProjectNotFoundException(""));
		this.mockMvc.perform(get("/projects/id/15")).andExpect(status().isNotFound());
	}

	@Test //Makes GET-request to domain/projects/{some string} and checks that it returns 400.
	public void badRequestToProjectById() {
		try {
			this.mockMvc.perform(get("/projects/id/string")).andExpect(status().isBadRequest());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
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

	/*
	@Test //Makes GET-request to domain/workoutputs and checks that it always returns http status code 200
	public void allWorkOutputsTest() {
		try {
			List all = new LinkedList();

			all.add(new WorkOutput("2008-09-08", ));
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
	*/


	@Test //Makes GET-request to domain/workoutputs/9999999999999 and checks that it returns http status code 404
	public void nonExistingProjectWorkOutputs() {
		try {
			URL url = new URL("http://localhost:8080/projects//workoutputs");

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			int expected = 404;
			assertEquals(expected, con.getResponseCode());

		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
	}

	@Test //Makes POST-request to domain/workoutputs with JSON-body that has all attributes that WorkOutput class constructor needs
	public void addWorkOutput() {
		try {
			URL url = new URL("http://localhost:8080/projects/add");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Accept", "application/json");

			JSONObject new_workOutput = new JSONObject(); //JSON object with right
			new_workOutput.put("Date", "");
			new_workOutput.put("hours", 2.5);
			new_workOutput.put("project_id", 1);
			new_workOutput.put("phase", "urakointi");
			new_workOutput.put("description", "suunnittelua");

			//Writes created JSON object into bytes that HTTP request understands
			OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
			wr.write(new_workOutput.toString()); //
			wr.flush();
			wr.close();

			assertEquals(200,con.getResponseCode());

		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
	}

	@Test //Makes GET-request to domain/projects/{some string} and checks that it returns 400.
	public void badRequestToWorkOutputs() {
		try {
			URL url = new URL("http://localhost:8080/workoutputs/string");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			assertEquals(400,con.getResponseCode());

		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
	}

}
