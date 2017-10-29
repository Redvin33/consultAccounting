package com.booking.consultAccounting;

import com.booking.consultAccounting.db.DBInit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
public class ConsultAccountingApplication {

	public static void main(String[] args) {
		System.out.println("Kaynnistys");
		//DBInit.createDatabase(); //Creates PostgreSQL database if it doesn't exist
		SpringApplication.run(ConsultAccountingApplication.class, args);
	}
}
