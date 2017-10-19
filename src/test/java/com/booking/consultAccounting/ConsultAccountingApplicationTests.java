package com.booking.consultAccounting;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ConsultAccountingApplicationTests {

	@Test
	public void contextLoads() {
	}
	@Test
	public void allItemsTest() {
		try {
			URL url = new URL("http://localhost:2015/projects");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.connect();
			assertEquals(200, con.getResponseCode());

		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
	}

	@Test
	public void nonExistingProject() {
		try {
			URL url = new URL("http://localhost:2015/projects/999999");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			assertEquals(404,con.getResponseCode());

		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
	}

	@Test
	public void createProject() {
		try {
			URL url = new URL("http://localhost:2015/projects");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Accept", "application/json");

			JSONObject new_project = new JSONObject();
			new_project.put("name", "testiprojekti");
			new_project.put("customer", "asiakas");
			new_project.put("hourly_rate", 78);
			new_project.put("charged", 1200);
			new_project.put("to_charge", 1500);
			new_project.put("phase", "urakointi");
			new_project.put("active", true);

			//Writes created JSON object into bytes that HTTP request understands
			OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
			wr.write(new_project.toString());
			wr.flush();
			wr.close();

			assertEquals(200,con.getResponseCode());

		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
	}

	@Test
	public void allWorkOutputsTest() {
		try {
			URL url = new URL("http://localhost:2015/workoutput");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			assertEquals(200, con.getResponseCode());

		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
	}

	@Test
	public void nonExistingProjectWorkOutputs() {
		try {
			URL url = new URL("http://localhost:2015/workoutput/99999");

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			int expected = 404;
			assertEquals(expected, con.getResponseCode());

		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
	}

	@Test
	public void addWorkOutput() {
		try {
			URL url = new URL("http://localhost:2015/workoutput");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Accept", "application/json");

			JSONObject new_workOutput = new JSONObject(); //JSON object with right
			new_workOutput.put("Date", "27/06/2017");
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


}
