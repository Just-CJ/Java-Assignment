package logAnalyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;
public class LogAnalyzer {
	

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File file=new File("access.log");
	      if(!file.exists()||file.isDirectory())
	             throw new FileNotFoundException();
	      FileInputStream fis=new FileInputStream(file);
	      InputStreamReader isw = new InputStreamReader(fis, "utf-8");  
	      BufferedReader br = new BufferedReader(isw); 
	      String line = br.readLine();
	      
	      int Data_size = 0;
	      int num_404 = 0;
	      List<Integer> ip_list_num = new ArrayList<Integer>();
	      List<String> ip_list = new ArrayList<String>();
	      
	      List<Integer> day_list_num = new ArrayList<Integer>();
	      List<String> day_list = new ArrayList<String>();
	      
	      List<Integer> url_list_num = new ArrayList<Integer>();
	      List<String> url_list = new ArrayList<String>();
	      
	      while(line != null){
	
	    	  Pattern ip_pattern = Pattern.compile("\\d+\\.\\d+\\.\\d+\\.\\d+"); //Æ¥Åäip
	    	  Matcher ip_matcher = ip_pattern.matcher(line);
	    	  if(ip_matcher.find()){
	    		  String curip = ip_matcher.group();
	    		  if(ip_list.contains(curip)){
	    			  int ip_index = ip_list.indexOf(curip);
	    			  ip_list_num.set(ip_index, ip_list_num.get(ip_index)+1);
	    		  }else{
	    			  ip_list.add(curip);
	    			  ip_list_num.add(1);
	    		  }
	    	  }
	    	  
	    	  Pattern time_pattern = Pattern.compile("\\[.+\\]"); //Æ¥Åätime
	    	  Matcher time_matcher = time_pattern.matcher(line);
	    	  if(time_matcher.find()){
	    		  String curday = time_matcher.group().replaceAll(":.+", "").replaceAll("\\[|\\]", "");
	    		  if(day_list.contains(curday)){
	    			  int day_index = day_list.indexOf(curday);
	    			  day_list_num.set(day_index, day_list_num.get(day_index)+1);
	    		  }else{
	    			  day_list.add(curday);
	    			  day_list_num.add(1);
	    		  }
	    		  
	    	  }
	    	  
	    	  
	    	  Pattern url_pattern = Pattern.compile("\\\".+\\\""); //Æ¥Åäurl
	    	  Matcher url_matcher = url_pattern.matcher(line);
	    	  if(url_matcher.find()){
	    		  String cururl = url_matcher.group().replaceAll("\"|\"", "")
	    				  							 .replaceAll("(GET )|(POST )", "")
	    				  							 .replaceAll(" HTTP.+", "");
	    		  
	    		  if(url_list.contains(cururl)){
	    			  int url_index = url_list.indexOf(cururl);
	    			  url_list_num.set(url_index, url_list_num.get(url_index)+1);
	    		  }else{
	    			  url_list.add(cururl);
	    			  url_list_num.add(1);
	    		  }

	    	  }
	    	  
	    	  Pattern data_pattern = Pattern.compile("\\d+$"); //Æ¥Åädata
	    	  Matcher data_matcher = data_pattern.matcher(line);
	    	  if(data_matcher.find()){
	    		  Data_size += Integer.valueOf(data_matcher.group());
	    	  }	  
	    	  
	    	  Pattern state_pattern = Pattern.compile("(\\d+ \\d+$)|(\\d+ -$)"); //Æ¥Åästate
	    	  Matcher state_matcher = state_pattern.matcher(line);
	    	  if(state_matcher.find()){
	    		  if(state_matcher.group().replaceAll(" \\d+$", "").replaceAll(" -$", "").equals("404"))
	    		  	num_404++;
	    	  }	
	    	  
	    	  line = br.readLine();
	      }
	      int max_url_num = url_list_num.get(0);
	      for(int i=1; i<url_list_num.size(); i++){
	    	  if(url_list_num.get(i)>max_url_num) max_url_num = url_list_num.get(i);
	      }
	      String max_url = url_list.get(url_list_num.indexOf(max_url_num));
	      
	      int max_day_num = day_list_num.get(0);
	      for(int i=1; i<day_list_num.size(); i++){
	    	  if(day_list_num.get(i)>max_day_num) max_day_num = day_list_num.get(i);
	      }
	      String max_day = day_list.get(day_list_num.indexOf(max_day_num));
	      
	      int max_ip_num = ip_list_num.get(0);
	      for(int i=1; i<ip_list_num.size(); i++){
	    	  if(ip_list_num.get(i)>max_ip_num) max_ip_num = ip_list_num.get(i);
	      }
	      String max_ip = ip_list.get(ip_list_num.indexOf(max_ip_num));
	      
	      
	      
	      System.out.println("The most popular page: "+max_url);
	      System.out.println("Ip which took the most pages: "+max_ip);
	      System.out.println("404 page number: "+num_404);
	      System.out.println("The busiest periods: "+max_day);
	      System.out.println("data being delivered to clients: "+Data_size);
	      
	      System.out.println("over");
	      br.close();
	}

}
