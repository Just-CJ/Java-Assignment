package techSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class keyword{
	private String key;
	private String info;
	public keyword(String key, String info){
		this.key = key;
		this.info = info;
	}
	
	String getKey(){
		return key;
	}
	
	String getInfo(){
		return info;
	}
}

public class TechSupport {
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		String[] input;
		String inputS;
		boolean found;
		List<keyword> keyList = new ArrayList<keyword>();
		
		for(int i=0; i<10; i++){
			keyword key = new keyword("key"+i, "information for key"+i);
			keyList.add(key);
		}
		
		
		
		
		System.out.println("Hello~ May I help you?");
		
		while(true){
			found = false;
			if(sc.hasNext()){
				inputS = sc.nextLine();
				if(inputS.equals("exit")) break;
				input = inputS.split(" ");
				for(String word : input){
					for(keyword key : keyList){
						if(word.matches("(.)*"+key.getKey()+"(.)*")){
							System.out.println(key.getInfo());
							found = true;
						}
					}
				}
				
				if(!found){
					System.out.println("No information found.");
				}
			}
		}
	}

}

