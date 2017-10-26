package com.booking.consultAccounting;

import com.booking.consultAccounting.db.DBInit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsultAccountingApplication {

	public static void main(String[] args) {
		DBInit.createDatabase(); //Creates PostgreSQL database if it doesn't exist
		SpringApplication.run(ConsultAccountingApplication.class, args);
	}
}
