// Server Class

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;

public class Server {
	private PrintWriter out;
	
	public static int calculate(String inputString) {
		return 0;
	}
	
	public Server(int port) {
		ServerSocket serverSocket;
		try {
			// Basic Declaration and Initialization
			String inputString = "";
			String outputString = "";
			
			// Server On
			serverSocket = new ServerSocket(port);
			out.println("Server Open.");
			
			while(true) {
				// Client Connection
				Socket connectionSocket = serverSocket.accept();
				out.println("Connected to Client.");
							
				// Initialize Client IO Streams
				BufferedReader clientInput = new BufferedReader(
						new InputStreamReader(connectionSocket.getInputStream())); 
				DataOutputStream  clientOutput = new DataOutputStream(
						connectionSocket.getOutputStream()); 
				
				// Client Input
				inputString = clientInput.readLine();
				
				// Validate Client Input
				if(inputString.equals("Kill")) {
					clientOutput.writeBytes("Kill Command\n");
					break;
				} else if(inputString.equals("Close")) {
					clientOutput.writeBytes("Closing Connection...\n");
				} else {
					// Process the Client Input
					outputString = String.valueOf(calculate(inputString)) + "\n";
					
					// Output Processed Data
					clientOutput.writeBytes(outputString);
				}
				
				
			}
			
			// Server Off
			serverSocket.close();
			out.println("Server Closed.");
		} catch (IOException ioe) {
				ioe.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		int port = 5003;
		@SuppressWarnings("unused")
		Server serverSocket = new Server(port);
	}

}
