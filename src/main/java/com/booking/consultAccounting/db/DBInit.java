package com.booking.consultAccounting.db;

import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

/**
 * Created by Lauri on 24.10.2017.
 */

//We are not using hmb2ddl.auto since its unsafe and seems to give errors. Instead we create database and its tables manually
public class DBInit {
    public static void createDatabase() {
        try {
            Properties props = new Properties();
            props.load(DBInit.class.getClassLoader().getResourceAsStream("db.properties"));

            String initurl = props.getProperty("initurl");
            String tableurl = props.getProperty("tableurl");
            String username = props.getProperty("username");
            String password = props.getProperty("password");
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(initurl, username, password);
            Statement statement = connection.createStatement();
            String sql = "CREATE DATABASE consultaccounting;";

            //To delete database: sql = "DROP DATABASE DBNAME";
            statement.executeUpdate(sql);

            System.out.println("Database created!");
            connection.close();
            DBtables.createTables(tableurl, username, password);
        }  catch (PSQLException sqlException) {
            if (sqlException.getErrorCode() == 0 ) {
                // Database already exists error
                System.out.println(sqlException.getMessage());
            } else {
                // Some other problems, e.g. Server down, no permission, etc
                sqlException.printStackTrace();
                System.out.println(sqlException.getErrorCode());
            }

        } catch (ClassNotFoundException e) {
            System.out.println("No driver class found");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
