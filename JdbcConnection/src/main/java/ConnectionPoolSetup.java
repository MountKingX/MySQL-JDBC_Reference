package main.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPoolSetup {

    // private static final String JDBC_DRIVER ="com.mysql.jdbc.Driver"; //deprecated
	private static final String DB_NAME = "test";
	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/"
			+ DB_NAME + "?useSSL=false";
    private static final String USERNAME = "";
    private static final String PASSWORD = "";


    // connection pool
    private static List<Connection> connectionPool = new ArrayList<Connection>();
    // private static JSONParser parser = new JSONParser();

    /**
     * method to get a mysql connection from connection pool
     *
     * @return one Connection
     * @throws Exception SQLException
     */
    private static synchronized Connection getConnection() throws Exception {
        // re-use a previous released connection
        if (connectionPool.size() > 0) {
            return connectionPool.remove(connectionPool.size() - 1);
        }

        // get class Name
        /*
         * deprecated try { Class.forName(JDBC_DRIVER);
         * //System.out.println("GET CLASS"); } catch (ClassNotFoundException e) { throw
         * new Exception(e); }
         */

        // get Connection
        try { // System.out.println("GET CONNECTION");
            return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    // release current connection to pool when leaving
    private static synchronized void releaseConnection(Connection con) {
        connectionPool.add(con);
    }

    public static void main(String[] args) throws SQLException {
        Connection con = null;
        try {
            con = getConnection();
            System.out.println("Connection established");

            // operation for DB
            // CRUD
            // create
            // read
            // update
            // delete
            // statement vs prepared-statement

        } catch (Exception e) {
            // System.out.println("exception got");
            e.printStackTrace();
        } finally {
            releaseConnection(con);
            connectionPool.clear();
            if (con != null) {
                con.close();
                System.out.println("Closed the connection successful!!");
            }
        }
    }
}
