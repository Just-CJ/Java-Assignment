package gradingSystem;

import java.sql.*;

public class CreateTable {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection c = null;
		Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:gradingSys.db");
	      
	      stmt = c.createStatement();
	      String sql = "CREATE TABLE GRADINGSYS " +
	                   "(NAME           TEXT    NOT NULL, " + 
	                   " COURSE         TEXT    NOT NULL, " + 
	                   " GRADE          INT     NOT NULL)";

	      stmt.executeUpdate(sql);
	      stmt.close();
	      c.close();
	      
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Create database successfully");
	}

}
