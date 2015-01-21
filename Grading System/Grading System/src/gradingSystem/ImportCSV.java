package gradingSystem;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Scanner;

public class ImportCSV {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String CSVfile;
		if(args.length == 0){
			Scanner sc = new Scanner(System.in);
			System.out.print("Input CSV file :");
			CSVfile = sc.nextLine();
			sc.close();
		}else{
			CSVfile = args[0];
		}
		
		Connection c = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:gradingSys.db");
	      
	      File file=new File(CSVfile);
	      if(!file.exists()||file.isDirectory())
	             throw new FileNotFoundException();
	      FileInputStream fis=new FileInputStream(file);
	      InputStreamReader isw = new InputStreamReader(fis, "GBK");  
	      BufferedReader br = new BufferedReader(isw); 
	      String line = br.readLine();
	      while(line != null){
	    	  String[] words = line.split(",");
	    	  InputScore.main(words);
	    	  line = br.readLine();
	      }
	      
	      br.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Import CSV file successfully");
 
	  }

}
