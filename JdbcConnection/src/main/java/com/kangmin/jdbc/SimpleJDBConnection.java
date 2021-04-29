package com.kangmin.jdbc;

import com.kangmin.jdbc.model.Account;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class SimpleJDBConnection {

    private static final String DB_NAME = "test";
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/" + DB_NAME + "?useSSL=false";
    private static final String USER_NAME = "";
    private static final String PASSWORD = "";

    public static void main(final String[] args) {
        try {
            // Class.forName("com.mysql.jdbc.Driver"); // deprecated
            System.out.println(">>> Try connecting to MySQL-DB at : " + JDBC_URL);
            final Connection myConn = DriverManager.getConnection(JDBC_URL, USER_NAME, PASSWORD);
            System.out.println(">>> Connecting successful!!");

            final String targetTableName = "accounts";
            final boolean isExist = tableExists("Accounts", myConn);
            final boolean isExistLowerCase = tableExists(targetTableName, myConn);
            System.out.println(">>> is Accounts Table exists? answer:" + isExist);
            System.out.println(">>> is Account Table exists? answer:" + isExistLowerCase);

            if (isExistLowerCase) {
                final String targetUserName = "sa";
                final Account u1 = TableUtil.read(targetTableName, targetUserName, myConn, null);
                if (u1 == null) {
                    Account sa = new Account();
                    sa.setId(89757);
                    sa.setUsername("sa");
                    sa.setPassword("password");
                    sa.setDisplayName("Super Admin");
                    final Account created = TableUtil.insert(targetTableName, sa, myConn, null);
                    System.out.println("just created user is" + created);

                } else {
                    System.out.println("existing sa user is" + u1);
                }
            } else {
                TableUtil.createUserTable(myConn, null);
            }

            myConn.close();
            System.out.println(">>> Closed the connection successful!!");
        } catch (final Exception exc) {
            exc.printStackTrace();
        }
    }

    private static boolean tableExists(
        final String tableName,
        final Connection con
    ) throws Exception {
        try {
            final DatabaseMetaData metaData = con.getMetaData();
            ResultSet rs = metaData.getTables(null, null, tableName, null);
            boolean answer = rs.next();
            rs.close();
            return answer;

        } catch (SQLException e) {
            try {
                con.close();
            } catch (SQLException e2) {
                /* ignore */
            }
            throw new SQLException(e);
        }
    }
}
