import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class calculater {

	
	public static int priority(String operator){
		switch(operator){
			case "(": case ")": return 1;
			case "+": case "-": return 4;
			case "*": case "/": case "%": return 3;
			default: return 0;
		}
	}
	
	public static int cal(int operand1, int operand2, String operator){
		switch(operator){
			case "+": return operand1+operand2;
			case "-": return operand1-operand2;
			case "*": return operand1*operand2;
			case "/": return operand1/operand2;
			case "%": return operand1%operand2;
			default: return 0;
		}
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		String input;
		boolean preIsNum = false;
		int result = 0;
		int leftbracket = 0, rightbracket = 0;

		List<StringBuffer> operandGroup = new ArrayList<StringBuffer>();
		List<String> operatorGroup = new ArrayList<String>();
		
		
		System.out.println("Input the expression(Enter \"exit\" to exit):");
		MainLoop:
		while(true){
		
			input = sc.nextLine();
				
			if(input.equals("exit")) break;
			//System.out.println(input);
				
			char[] inputArray = input.toCharArray();
			//System.out.println(inputArray.length);
			int elementLeft = inputArray.length;
			ElementLoop:
			for(char element : inputArray){
				switch(element){
					case '+': case '-': case '*': case '/': case '%': case '(': case ')':
					{
						preIsNum = false;
						operatorGroup.add(String.valueOf(element));
						}break;
							
					case '0': case '1': case '2': case '3': case '4': 
					case '5': case '6': case '7': case '8': case '9':
					{
						if(preIsNum){
							operandGroup.get(operandGroup.size()-1).append(element);
						}else{
							operandGroup.add(new StringBuffer(String.valueOf(element)));
							//System.out.println(operandGroup.get(operandGroup.size()-1).toString());
							preIsNum = true;
						}
						
					}break;
					case ' ': break;
					default: {
						preIsNum = false;
						leftbracket = 0; 
						rightbracket = 0;
						operandGroup.clear();
						operatorGroup.clear();
						System.out.println("Error with the expression! Please input again:");
						continue MainLoop;
					}
				}
				
				if(!preIsNum){
					elementLeft--;
					try{
						while(true){
							//System.out.println("test");
							if(operatorGroup.get(operatorGroup.size()-1).equals("(")){
								if(++leftbracket <= rightbracket){
									preIsNum = false;
									leftbracket = 0; 
									rightbracket = 0;
									
									operandGroup.clear();
									operatorGroup.clear();
									System.out.println("Error with the expression! Please input again:");
									continue MainLoop;
								}
							}
							
							
							if(operatorGroup.size() >= 2){
								if(operatorGroup.get(operatorGroup.size()-2).equals("(") &&
								   !operatorGroup.get(operatorGroup.size()-1).equals(")")){
									break;
								}
								if(operatorGroup.get(operatorGroup.size()-1).equals(")")){
									if(++rightbracket > leftbracket){
										preIsNum = false;
										leftbracket = 0; 
										rightbracket = 0;
										
										operandGroup.clear();
										operatorGroup.clear();
										System.out.println("Error with the expression! Please input again:");
										continue MainLoop;
									}
									
									operatorGroup.remove(operatorGroup.size()-1);
									while(!operatorGroup.get(operatorGroup.size()-1).equals("(")){
										//System.out.println("test");
										result = cal(Integer.valueOf(operandGroup.get(operandGroup.size()-2).toString()).intValue(), 
													 Integer.valueOf(operandGroup.get(operandGroup.size()-1).toString()).intValue(),
													 operatorGroup.get(operatorGroup.size()-1));
										
										operatorGroup.remove(operatorGroup.size()-1);
										operandGroup.remove(operandGroup.size()-1);
										operandGroup.remove(operandGroup.size()-1);
										operandGroup.add(new StringBuffer(String.valueOf(result)));
									}
									
									operatorGroup.remove(operatorGroup.size()-1);
									break;
								}
								else if(priority(operatorGroup.get(operatorGroup.size()-1)) >=
										priority(operatorGroup.get(operatorGroup.size()-2))){
									
									result = cal(Integer.valueOf(operandGroup.get(operandGroup.size()-2).toString()).intValue(), 
											 Integer.valueOf(operandGroup.get(operandGroup.size()-1).toString()).intValue(),
											 operatorGroup.get(operatorGroup.size()-2));
									
									operatorGroup.remove(operatorGroup.size()-2);
									operandGroup.remove(operandGroup.size()-1);
									operandGroup.remove(operandGroup.size()-1);
									operandGroup.add(new StringBuffer(String.valueOf(result)));
									break;
								}else break;
							}else break;
						}
					}catch(ArrayIndexOutOfBoundsException e){
						preIsNum = false;
						leftbracket = 0; 
						rightbracket = 0;
						operandGroup.clear();
						operatorGroup.clear();
						System.out.println("Error with the expression! Please input again:");
						continue MainLoop;
					}catch(ArithmeticException e){
						preIsNum = false;
						leftbracket = 0; 
						rightbracket = 0;
						operandGroup.clear();
						operatorGroup.clear();
						System.out.println("Error with the expression! Please input again:");
						continue MainLoop;
					}
					
				}
				
			}
			
			if(operandGroup.isEmpty()){
				preIsNum = false;
				leftbracket = 0; 
				rightbracket = 0;
				operandGroup.clear();
				operatorGroup.clear();
				System.out.println("Error with the expression! Please input again:");
				continue;
			}
			
			
			try{
				while(!operatorGroup.isEmpty()){
					
					result = cal(Integer.valueOf(operandGroup.get(operandGroup.size()-2).toString()).intValue(), 
							 Integer.valueOf(operandGroup.get(operandGroup.size()-1).toString()).intValue(),
							 operatorGroup.get(operatorGroup.size()-1));
					
					
					operatorGroup.remove(operatorGroup.size()-1);
					operandGroup.remove(operandGroup.size()-1);
					operandGroup.remove(operandGroup.size()-1);
					operandGroup.add(new StringBuffer(String.valueOf(result)));
					
				}
			}catch(ArrayIndexOutOfBoundsException e){
				preIsNum = false;
				leftbracket = 0; 
				rightbracket = 0;
				operandGroup.clear();
				operatorGroup.clear();
				System.out.println("Error with the expression! Please input again:");
				continue MainLoop;
			}catch(ArithmeticException e){
				preIsNum = false;
				leftbracket = 0; 
				rightbracket = 0;
				operandGroup.clear();
				operatorGroup.clear();
				System.out.println("Error with the expression! Please input again:");
				continue MainLoop;
			}
			
			System.out.println("Result: " + operandGroup.get(0).toString());
			preIsNum = false;
			leftbracket = 0; 
			rightbracket = 0;
			System.out.println("Input the expression(Enter \"exit\" to exit):");
			operandGroup.remove(0);
		
			
		}
		sc.close();
	}

}
