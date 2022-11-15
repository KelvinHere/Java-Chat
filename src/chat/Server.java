package Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{
	ServerSocket serverSocket;
	
	public Server() {
		try {
			serverSocket = new ServerSocket(5000);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void go() {
		try {
			while (true) {
				// Create anon socket for client to attach
				System.out.println("Waiting");
				Socket sock = serverSocket.accept();
				PrintWriter writer = new PrintWriter(sock.getOutputStream());
				writer.println("Connected on port :" + sock.getPort());
				writer.flush();
				System.out.println("Client assigned port :" + sock.getPort());
				
				InputStreamReader stream = new InputStreamReader(sock.getInputStream());
				BufferedReader br = new BufferedReader(stream);
				System.out.println(br.readLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		go();		
	}
}
