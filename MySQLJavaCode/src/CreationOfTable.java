import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


class User {
	private int uid;
    private String userName;  // unique email address
    private String password;
    private String firstName;
    private String lastName;
    
    public int getUid()        		   { return uid;       }
    public String getPassword()        { return password;  }
    public String getUserName()        { return userName;  }
    public String getFirstName()       { return firstName; }
    public String getLastName()        { return lastName;  }

    public void setUid(String s)       { password = s;  }
    public void setPassword(String s)  { password = s;  }
    public void setUserName(String s)  { userName = s;  }
    public void setFirstName(String s) { firstName = s; }
    public void setLastName(String s)  { lastName = s;  }
}


public class CreationOfTable {

	private static final String USERNAME = "";
	private static final String PASSWORD = "";
	private static final String DATABASE = "test";
	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/" + DATABASE + "?useSSL=false";
	private static List<Connection> connectionPool = new ArrayList<Connection>();
	
    private static synchronized Connection getConnection() throws Exception {
        if (connectionPool.size() > 0) {
            return connectionPool.remove(connectionPool.size() - 1);
        }
        try {
            return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }
    
    private static synchronized void releaseConnection(Connection con) {
        connectionPool.add(con);
    }
    
    private boolean tableExists() throws Exception {
        Connection con = null;
        String tableName = "user";
        
        try {
            con = getConnection();
            DatabaseMetaData metaData = con.getMetaData();
            ResultSet rs = metaData.getTables(null, null, tableName, null);

            boolean answer = rs.next();

            rs.close();
            releaseConnection(con);

            return answer;

        } catch (SQLException e) {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException e2) { /* ignore */
            }
            throw new SQLException(e);
        }
    }
    
    private void createUserTable() throws SQLException {
        Connection con = null;
        String tableName = "user";

        try {
			con = getConnection();
	        Statement stmt = con.createStatement();
	        stmt.executeUpdate("CREATE TABLE " + tableName
	        		+ " (id int not null auto_increment, "
	        		+ "userName VARCHAR(255), "
	        		+ "password VARCHAR(255), "
	        		+ "firstName VARCHAR(255), "
	        		+ "lastName VARCHAR(255), "
	                + "PRIMARY KEY(id))");
	        stmt.close();
	        releaseConnection(con);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    
    public User read(String userName) throws SQLException {
        Connection con = null;
        String tableName = "user";
        
        try {
            con = getConnection();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM "
                    + tableName + " WHERE userName=?");
            pstmt.setString(1, userName);
            ResultSet rs = pstmt.executeQuery();

            User user;
            if (!rs.next()) {
                user = null;
            } else {
                user = new User();
                user.setUserName(rs.getString("userName"));
                user.setPassword(rs.getString("password"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
            }

            rs.close();
            pstmt.close();
            releaseConnection(con);
            return user;

        } catch (Exception e) {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException e2) { /* ignore */
            }
            throw new SQLException(e);
        }
    }
}
