package gradingSystem;

import java.sql.*;
import java.util.Scanner;

public class InputScore {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String name, course, score;
		Scanner sc = new Scanner(System.in);
		if(args.length != 3){
			System.out.print("Input name:");
			name = sc.nextLine();
			System.out.print("Input course:");
			course = sc.nextLine();
			System.out.print("Input score:");
			score = sc.nextLine();
		}else{
			name = args[0];
			course = args[1];
			score = args[2];
		}
		
		//System.out.println(name+course+score);
		
		Connection c = null;
		Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:gradingSys.db");
	      
	      stmt = c.createStatement();
	      String sql_select = "SELECT * FROM GRADINGSYS "+
	    		  		"WHERE name=\'"+name+"\' and COURSE=\'"+course+"\';";
	      ResultSet rs = stmt.executeQuery(sql_select);
	      if(rs.isAfterLast()){ //没有记录，插入一条新的
	    	  String sql_insert = "INSERT INTO GRADINGSYS (NAME, COURSE, GRADE) " +
	                   "VALUES (\'"+name+"\', \'"+course+"\', "+score+" );"; 
	    	  stmt.executeUpdate(sql_insert);
	      }else{ // 已有记录，仅作修改
	          String sql_update = "UPDATE GRADINGSYS set GRADE = "+score+" where NAME= \'"+name+"\' and COURSE=\'"+course+"\';";
	          stmt.executeUpdate(sql_update);
	      }

	      rs.close();
	      stmt.close();
	      //c.commit();
	      c.close();
	      
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Insert item successfully");
	    sc.close();
 
	  }

}
