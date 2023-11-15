/* Client Class
 * 
 *

import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.Socket;

public class Client {
	private static PrintWriter out;
	private static String randInput(String inputString) {
		inputString = String.valueOf((int)(Math.random() * 100 + 1));
		
		for(int i = 0; i < (int)(Math.random() * 5 + 1); i++) {
			int d4 = (int)(Math.random() * 5 + 1);
			if(d4 == 1) {
				inputString += " + " + String.valueOf((int)(Math.random() * 100 + 1));
			} else if(d4 == 2) {
				inputString += " - " + String.valueOf((int)(Math.random() * 100 + 1));
			} else if(d4 == 3) {
				inputString += " * " + String.valueOf((int)(Math.random() * 100 + 1));
			} else if(d4 == 4) {
				inputString += " / " + String.valueOf((int)(Math.random() * 100 + 1));
			}
		}
		out.println("Equation: " + inputString);
		return inputString;
	}
	
	public static void displayPrompt() {
		out.println("Enter a series of numbers to calculate: ");
		out.println("Enter spaces between the operands and operators.\n"
				+ "Only use the operators +-/");
	}
	
	public Client(String address, int port, boolean randTest) {
		try {
			// Basic Declaration and Initialization
			String inputString = "";
			String serverString = "";
			//boolean randTest = true;
			
			// Client On
			Socket clientSocket = new Socket(address, port);
			out.println("Client Open.");
			
			// Initialize User Input
			BufferedReader userInput = new BufferedReader( 
					new InputStreamReader(System.in));
			
			// Initialize Server IO Stream
			DataOutputStream serverOutput = new DataOutputStream(
					clientSocket.getOutputStream());
			BufferedReader serverInput = new BufferedReader(
					new InputStreamReader(clientSocket.getInputStream()));
			
			// User Input
			if(!randTest) {
				displayPrompt();
				inputString = userInput.readLine();
			}
			
			// Random Input(Test)
			if(randTest) {
				inputString = randInput(inputString);
			}
			
			// Server Send
			serverOutput.writeBytes(inputString + "\n");
						
			// Server Receive 
			serverString = serverInput.readLine();
			out.println("Server Response: " + serverString);
			
			// Client Off
			clientSocket.close();
			out.println("Client Closed.");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		// Address and Port Init.
		String address = "127.0.0.1";
		int port = 5002;
		
		// Client Creation
		@SuppressWarnings("unused")
		Client clientSocket = new Client(address, port, false);
	}

}
*/