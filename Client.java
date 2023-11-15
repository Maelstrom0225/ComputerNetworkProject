// Client Class

import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;

public class Client {
	
	public Client(String address, int port) {
		try {
			String serverString = "";
			String nameString = "";
			
			// Client On
			Socket clientSocket = new Socket(address, port);
			System.out.println("Client Open.");
			
			// Initialize User Input
			BufferedReader userInput = new BufferedReader( 
					new InputStreamReader(System.in));
			
			// Initialize Server IO Stream
			DataOutputStream serverOutput = new DataOutputStream(
					clientSocket.getOutputStream());
			BufferedReader serverInput = new BufferedReader(
					new InputStreamReader(clientSocket.getInputStream()));
			
			// Enter Name
			System.out.println("Enter name: ");
			nameString = userInput.readLine();
			serverOutput.writeBytes(nameString + "\n");
			
			
			while (!serverString.equals("q") && !nameString.equals("Terminate")) {
				// Basic Declaration and Initialization
				String inputString = "";
				serverString = "";
				
				// User Input
				System.out.println("Enter message: ");
				inputString = userInput.readLine();
				
				// Server Send
				serverOutput.writeBytes(inputString + "\n");
				
				// Server Receive 
				serverString = serverInput.readLine();
				System.out.println("Server Response: " + serverString);
			
			}
			
			// Client Off
			clientSocket.close();
			System.out.println("Client Closed.");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		// Address and Port Init.
		String address = "127.0.0.1";
		int port = 6665;
		
		// Client Creation
		@SuppressWarnings("unused")
		Client clientSocket = new Client(address, port);		
	}
}

