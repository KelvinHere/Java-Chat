package Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server{
	ServerSocket serverSocket;
	ArrayList<PrintWriter> clientOutputStreams;
	
	public Server() {
		try {
			serverSocket = new ServerSocket(5005);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void go() {
		clientOutputStreams = new ArrayList<>();
		try {
			while (true) {
				System.out.println("Waiting");
				// Create anon socket for client to attach
				Socket clientSocket = serverSocket.accept();
				System.out.println("Client connected");
				PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
				clientOutputStreams.add(writer);
				
				Thread t = new Thread(new ClientHandler(clientSocket));
				t.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public class ClientHandler implements Runnable {
		BufferedReader reader;
		Socket clientSocket;
		
		public ClientHandler(Socket clientSocket) {
			try {
				this.clientSocket = clientSocket;
				InputStreamReader inputStream = new InputStreamReader(clientSocket.getInputStream());
				reader = new BufferedReader(inputStream);
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			String message;
			try {
				while ((message = reader.readLine()) != null) {
					System.out.println("Recieved client message : " + message);
					sendMessage(message);
					Thread.sleep(250);
				}
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		
		public void sendMessage(String message) {
			for (PrintWriter writer : clientOutputStreams) {
				try {
					writer.println(message);
					writer.flush();
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}
}
