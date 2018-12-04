import java.sql.Connection;
import java.sql.DriverManager;

public class TestDBConnection {

    public static void main(String[] args) {

	String DB_NAME = "testc";
	String jdbcUrl = "jdbc:mysql://localhost:3306/" + DB_NAME + "?useSSL=false";
	String USER_NAME = "";
	String PASSWORD = "";
	try {
	    // Class.forName("com.mysql.jdbc.Driver");
	    System.out.println("Try connecting to MysQL-DB at : " + jdbcUrl + "\n");
	    Connection myConn = DriverManager.getConnection(jdbcUrl, USER_NAME, PASSWORD);
	    System.out.println("Connecting successful!!");
	    myConn.close();
	    System.out.println("Closed the connection successful!!");
	} catch (Exception exc) {
	    exc.printStackTrace();
	}

    }

}
