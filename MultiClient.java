import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MultiClient {
	public static Socket clientSocket;
	public static String[] input = {"2 + 2", "5+5", "4+4"};
	
	public static void start(String address, int port) {
		try {
			int num = 3;
			for(int i = 0; i < num; i++) {
				clientSocket = new Socket(address, port);
				NewClient clientInstance = new NewClient(clientSocket, input[i]);
				System.out.println("Client Instance Created.");
				clientInstance.run();
			}
			
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public static void stop() {
		// Client Off
		try {
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Client Closed.");
	}
	
	
	
	public static class NewClient extends Thread {
		private Socket clientSocket;
		private String clientRequest = "Empty";
		
		public NewClient(Socket socket, String inputString) {
			this.clientSocket = socket;
			this.clientRequest = inputString;
		}
		
		public void run() {
			String serverString = "";
			
			try {
				// Basic Declaration and Initialization
				serverString = "";
				
				// Initialize Server IO Stream
				DataOutputStream serverOutput = new DataOutputStream(
						clientSocket.getOutputStream());
				BufferedReader serverInput = new BufferedReader(
						new InputStreamReader(clientSocket.getInputStream()));
				
				// Server Send
				serverOutput.writeBytes(clientRequest + "\n");
							
				// Server Receive 
				serverString = serverInput.readLine();
				System.out.println("Server Response: " + serverString);
				
				
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			
		}
	}
	public static void main(String[] args) {
		String address = "127.0.0.1";
		int port = 6060;
		start(address, port);
	}
}
