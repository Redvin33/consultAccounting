package com.booking.consultAccounting.DB;

import org.postgresql.util.PSQLException;

import java.sql.*;

/**
 * Created by Lauri on 24.10.2017.
 */
public class DBInit {
    public static void createDatabase() {
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/",
                    "postgres", "olo8feimi");
            statement = connection.createStatement();
            String sql = "CREATE DATABASE consultaccounting;";

            //To delete database: sql = "DROP DATABASE DBNAME";
            statement.executeUpdate(sql);
            System.out.println("Database created!");
        }catch (PSQLException e) {
            System.out.println("Database already exists.");
        } catch (SQLException sqlException) {
            if (sqlException.getErrorCode() == 1007) {
                // Database already exists error
                System.out.println(sqlException.getMessage());
            } else {
                // Some other problems, e.g. Server down, no permission, etc
                sqlException.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("No driver class found");
        }
    }
}
