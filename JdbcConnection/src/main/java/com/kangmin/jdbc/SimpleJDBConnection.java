package com.kangmin.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;


public final class SimpleJDBConnection {

    private static final String DB_NAME = "test";
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/" + DB_NAME + "?useSSL=false";
    private static final String USER_NAME = "";
    private static final String PASSWORD = "";

    public static void main(final String[] args) {
        try {
            // Class.forName("com.mysql.jdbc.Driver"); // deprecated
            System.out.println(">>> Try connecting to MysQL-DB at : " + JDBC_URL);
            final Connection myConn = DriverManager.getConnection(JDBC_URL, USER_NAME, PASSWORD);
            System.out.println(">>> Connecting successful!!");
            myConn.close();
            System.out.println(">>> Closed the connection successful!!");
        } catch (final Exception exc) {
            exc.printStackTrace();
        }
    }
}
