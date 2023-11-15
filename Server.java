/* Server Class
 * 
 * 
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server {
	private static PrintWriter out;
	
	public static int calculate(String inputString) {
		// Basic Initializations
		int result = 0;
		int secondValue = 0;
		int tempInt = 0;
		String[] inputStringArray;
		String eqnString = "";
		
		// Split Array
		inputStringArray = inputString.split(" ");
		
		// Print Array/Equation
		for(int i = 0; i < inputStringArray.length; i++) {
			eqnString += inputStringArray[i];
		}
		out.println("Equation: " + eqnString);
		
		// First Value is set
		tempInt = 0;
		for(int i = 0; i < inputStringArray[0].length(); i++) {
			tempInt = Integer.parseInt(String.valueOf(inputStringArray[0].charAt(i))) * 
					(int)(Math.pow(10, (inputStringArray[0].length() - 1 - i)));
			result += tempInt;
		}
		
		// Loop for Other Operands 
		for(int h = 0; h < (inputStringArray.length / 2); h++) {
			// Initialization
			tempInt = 0;
			secondValue = 0;
			int charIndex = h * 2 + 1;
			int valueIndex = h * 2 + 2;
			
			// Decode Other Operand
			for(int i = 0; i < inputStringArray[valueIndex].length(); i++) {
				tempInt = Integer.parseInt(String.valueOf(inputStringArray[valueIndex].charAt(i))) * 
						(int)(Math.pow(10, (inputStringArray[valueIndex].length() - 1 - i)));
				secondValue += tempInt;
			}
			
			// Decode Operator and Operate
			if(inputStringArray[charIndex].equals("+")) {
				result += secondValue;
			} else if(inputStringArray[charIndex].equals("-")) {
				result -= secondValue;
			} else if(inputStringArray[charIndex].equals("*")) {
				result *= secondValue;
			} else if(inputStringArray[charIndex].equals("/")) {
				result /= secondValue;
			} else {
				out.println("Format Error");
			}
		}
		return result;
	}
	
	public Server(int port) {
		ServerSocket serverSocket;
		try {
			// Basic Declaration and Initialization
			String inputString = "";
			String outputString = "";
			ArrayList<ArrayList<String>> serverDatabase = new ArrayList<ArrayList<String>>();
			
			// Server On
			serverSocket = new ServerSocket(port);
			out.println("Server Open.");
			
			while(true) {
				// Client Connection
				Socket connectionSocket = serverSocket.accept();
				out.println("Connected to Client.");
				ArrayList<String> userInfo = new ArrayList<String>();
				
				// Initialize Client IO Streams
				BufferedReader clientInput = new BufferedReader(
						new InputStreamReader(connectionSocket.getInputStream())); 
				DataOutputStream  clientOutput = new DataOutputStream(
						connectionSocket.getOutputStream()); 
				
				// Client Input
				inputString = clientInput.readLine();
				//say("Client: " + inputString);
				
				// Validate Client Input
				if(inputString.equals("Kill")) {
					clientOutput.writeBytes("Kill Command\n");
					break;
				} else if(inputString.equals("Database")) {
					out.println("Database Info: ");
					out.println("Database Size: " + serverDatabase.size());
					for(int i = 0; i < serverDatabase.size(); i++) {
						out.println("User " + i + ": " + serverDatabase.get(i).get(0) + " = " + serverDatabase.get(i).get(1));
					}
					clientOutput.writeBytes("Database Command\n");
					break;
				} 
				
				// Process the Client Input
				outputString = String.valueOf(calculate(inputString)) + "\n";
				
				// Output Processed Data
				clientOutput.writeBytes(outputString);
				
				// Save User Info
				userInfo.add(String.valueOf(inputString));
				userInfo.add(String.valueOf(outputString));
				serverDatabase.add(userInfo);
				
				// Echo Client Data
				out.println("Client " + serverDatabase.size() + ": ");
				out.println("Equation: " + userInfo.get(0));
				out.println("Result: " + userInfo.get(1));
			}
			
			// Server Off
			serverSocket.close();
			out.println("Server Closed.");
		} catch (IOException ioe) {
				ioe.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		int port = 5002;
		@SuppressWarnings("unused")
		Server serverSocket = new Server(port);
	}

}
