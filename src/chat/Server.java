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
			serverSocket = new ServerSocket(5000);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void go() {
		clientOutputStreams = new ArrayList<>();
		try {
			while (true) {
				// Create anon socket for client to attach
				Socket clientSocket = serverSocket.accept();
				System.out.println("Client assigned port :" + clientSocket.getPort());
				PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
				clientOutputStreams.add(writer);
				
				Thread t = new Thread(new ClientHandler(clientSocket));
				t.run();
				writer.println("Client connected on port :" + clientSocket.getPort());
				writer.flush();
				
				InputStreamReader stream = new InputStreamReader(clientSocket.getInputStream());
				BufferedReader br = new BufferedReader(stream);
				System.out.println(br.readLine());
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
				}
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
