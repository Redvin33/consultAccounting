package com.booking.consultAccounting.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Created by Lauri on 26.10.2017.
 */
//Creates tables workoutputs and projects to database
public class DBtables {
    public static void createTables(String url, String name, String pass) {
        try {
            Connection connection = DriverManager.getConnection(url, name, pass);
            String sql = "";
            Statement statement = connection.createStatement();
            sql = "CREATE TABLE projects(" +
                    "id SERIAl unique," +
                    "name varchar," +
                    "customer varchar," +
                    "hourly_rate float," +
                    "charged float," +
                    "to_charge float," +
                    "phase varchar," +
                    "active boolean," +
                    "primary key(id)," +
                    "unique(name)" +
                    ");";
            statement.executeUpdate(sql);
            System.out.println("Table projects created!");
            sql = "CREATE TABLE workoutputs(" +
                    "id SERIAL unique," +
                    "pvm date," +
                    "hours float," +
                    "project int," +
                    "paid varchar," +
                    "description varchar," +
                    "primary key(id)," +
                    "foreign key(project) references Projects(id)" +
                    ");";
            statement.executeUpdate(sql);
            System.out.println("Table work_output created!");
            sql ="CREATE SEQUENCE hibernate_sequence;";
            statement.executeUpdate(sql);
            System.out.println("Hibernate_sequence created!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
