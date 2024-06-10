package com.liebniz.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {

    private static final String JDBC_URL = "jdbc:mysql://localhost/my-books-test";
    private static final String JDBC_USER = System.getenv("MYSQL_USERNAME");
    private static final String JDBC_PASSWORD = System.getenv("MYSQL_PASSWORD");

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
