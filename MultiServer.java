// MultiServer Class

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MultiServer {
	private static ServerSocket server;
	public static ArrayList<ArrayList<String>> clientArray = new ArrayList<ArrayList<String>>();
	
	public static void start(int port) {
		try {
			// Create serverSocket
			server = new ServerSocket(port);
			System.out.println("Server Started.");
			
			// Server Run Loop
			while(true) {
				// Create Client Handler
				ClientHandler client = new ClientHandler(server.accept());
				System.out.println("New ClientHandler Started.");
				
				// Run Client Handler
				client.run();
				
				// Print Database Info
				for(int i = 0; i < clientArray.size(); i++) {
					System.out.println(clientArray.get(i));
				}
				
				if(clientArray.get(clientArray.size() - 1).get(0).equals("Terminate")) {
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}
	
	public void stop() {
		try {
			// Close Server
			server.close();
			System.out.println("Server Closed.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static class ClientHandler extends Thread {
		private Socket welcomeSocket;
		private BufferedReader in;
		DataOutputStream  clientOutput;
		
		public ClientHandler(Socket socket) {
			this.welcomeSocket = socket;		
		}
		
		public void run() {
			String input;
			try {
				// Client I/O
				clientOutput = new DataOutputStream(welcomeSocket.getOutputStream()); 
				in = new BufferedReader(new InputStreamReader(welcomeSocket.getInputStream()));
				
				// Client Name Info Saving
				ArrayList<String> clientInfo = new ArrayList<String>();
				clientInfo.add(in.readLine());
				clientArray.add(clientInfo);
				
				// Client Running Loop
				while((input = in.readLine()) != null) {
					// Commands for Client
					if("Terminate".equals(clientInfo.get(0))) {
						clientOutput.writeBytes("q");
						break;
					} else if("q".equals(input)) {
						clientOutput.writeBytes("q");
						System.out.println("Client Disconnected.");
						break;
					}
					
					// Client Request Info Saving
					clientInfo.add(input);
					
					// Calculator CODE
					String[] arrayStore = input.split("[\\+\\-\\*\\/]");//-*/
					
					int num1 = Integer.parseInt(arrayStore[0]);
					int num2 = Integer.parseInt(arrayStore[1]);
					
					int symPlus = input.indexOf('+');
					int symMin = input.indexOf('-');
					int symMult = input.indexOf('*');
					int symDiv = input.indexOf('/');
					
					int result = 0;
					
					if(symPlus != -1) {
						result = num1 + num2;
					}
					else if(symMin != -1) {
						result = num1 - num2;
					}
					else if(symMult != -1) {
						result = num1 * num2;
					}
					else if(symDiv != -1) {
						result = num1 / num2;
					}
					
					// Return Result of Calculation
					clientInfo.add(""+result);
					clientOutput.writeBytes(result+"\n");
					//END OF THE CALCULATOR CODE
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				// Close I/O and Socket
				in.close();
				clientOutput.close();
				welcomeSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	public static void main(String[] args) {
		int port = 6665;
		start(port);
	}
}

