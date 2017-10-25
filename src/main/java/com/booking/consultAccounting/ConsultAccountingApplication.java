package com.booking.consultAccounting;

import com.booking.consultAccounting.DB.DBInit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class ConsultAccountingApplication {

	public static void main(String[] args) {
		DBInit.createDatabase();
		SpringApplication.run(ConsultAccountingApplication.class, args);
	}
}
