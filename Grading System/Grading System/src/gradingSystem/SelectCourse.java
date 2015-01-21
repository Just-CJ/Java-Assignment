package gradingSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class SelectCourse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String course;
		if(args.length == 0){
			Scanner sc = new Scanner(System.in);
			System.out.print("Input course name :");
			course = sc.nextLine();
			sc.close();
		}else{
			course = args[0];
		}
		
		Connection c = null;
		Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:gradingSys.db");
	      
	      stmt = c.createStatement();
	      String sql = "SELECT * FROM GRADINGSYS WHERE course = \'"+course+"\';";

	      ResultSet rs = stmt.executeQuery(sql);
	      int Size = 0;
	      float mark_all = 0;
	      while ( rs.next() ) {
	    	  Size++;
	          String  name = rs.getString("name");
	          int score  = rs.getInt("grade");
	          mark_all += score;
	          System.out.println(name+"\t"+course+"\t"+score);
	       }
	      
	      if(Size != 0){
	    	  System.out.println("Student Number: "+Size);
	    	  System.out.println("Average Mark: "+mark_all/Size);
	      }else{
	    	  System.out.println("Nothing found!");
	      }
	      
	      stmt.close();
	      c.close();
	      
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
		
	    //System.out.println("Opened database successfully");
	}

}
