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
			int responsecode = con.getResponseCode();
			assertEquals(responsecode, 200);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
	}

	@Test
	public void nonExistingProject() {
		try {
			URL url = new URL("http://localhost:2015/projects/999999");

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			int responsecode = connection.getResponseCode();
			int expected = 404;
			System.out.println(responsecode);
			assertEquals(expected,responsecode);

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
			OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
			wr.write(new_project.toString());
			wr.flush();
			wr.close();

			int responsecode = con.getResponseCode();
			String s = con.getResponseMessage();
			int expected = 200;
			assertEquals(expected,responsecode);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
	}



}
