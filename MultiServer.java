//

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiServer {
	private static ServerSocket server;
	private static void say(String m) {
		System.out.println(m);
	}
	
	public static void start(int port) {
		try {
			server = new ServerSocket(port);
			say("Server Started.");
			while(true) {
				ClientHandler client = new ClientHandler(server.accept());
				say("New ClientHandler Started.");
				client.run();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}
	
	public void stop() {
		try {
			server.close();
			say("Server Closed.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static class ClientHandler extends Thread {
		private Socket welcomeSocket;
		private PrintWriter out;
		private BufferedReader in;
		
		public ClientHandler(Socket socket) {
			this.welcomeSocket = socket;		
		}
		
		public void run() {
			try {
				out = new PrintWriter(welcomeSocket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(welcomeSocket.getInputStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			String input;
			try {
				while((input = in.readLine()) != null) {
					if(".".equals(input)) {
						out.println("working");
						break;
					} else if("Exit".equals(input)) {
						out.println("Exit");
						say("Client Disconnected.");
						break;
					}
					out.println(input);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				in.close();
				out.close();
				welcomeSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	public static void main(String[] args) {
		int port = 6060;
		start(port);
	}
}

