package Chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{
	ServerSocket serverSocket; 
	
	public Server() {
		try {
			serverSocket = new ServerSocket(5000);
			go();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void go() {
		try {
			while (true) {
				// Create anon socket for client to attach
				System.out.println("Waiting");
				Socket sock = serverSocket.accept();
				PrintWriter writer = new PrintWriter(sock.getOutputStream());
				writer.println("Connected on port :" + sock.getPort());
				writer.flush();
				System.out.println("Client assigned port :" + sock.getPort());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
