package system;
import java.sql.*;

public class AWSDBConnectionMgr {	
	private static String dbName, userName, password, hostname, port, jdbcUrl;
	public AWSDBConnectionMgr() {
		if (System.getProperty("RDS_HOSTNAME") != null) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			    dbName = System.getProperty("RDS_DB_NAME");
			    userName = System.getProperty("RDS_USERNAME");
			    password = System.getProperty("RDS_PASSWORD");
			    hostname = System.getProperty("RDS_HOSTNAME");
			    port = System.getProperty("RDS_PORT");
			    jdbcUrl = "jdbc:mysql://" + hostname + ":" +
			    	    port + "/" + dbName + "?user=" + userName + "&password=" + password;
			}catch (ClassNotFoundException e) { System.out.println(e.toString());}
		}
	}
	private static Connection getRemoteConnection() {
	    if (System.getProperty("RDS_HOSTNAME") != null) {
	      try {
	    	  Connection con = DriverManager.getConnection(jdbcUrl);
	    	  System.out.println("Remote connection successful.");
	    	  return con;
	      }catch (SQLException e) { System.out.println(e.toString());}
	    }
	    return null;
	}
	
	public Connection getConnection() throws SQLException{
		Connection conn = null;
		try {
			conn = getRemoteConnection();
		}catch(Exception e){
			
		}
		
		return conn;
	}
}
