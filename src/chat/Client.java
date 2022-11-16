package Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Client {
	String clientName = "Bob";
	Socket chatSocket;
	BufferedReader reader;
	JTextField outgoing;
	JTextArea messages;

	public Client(String name) {
		this();
		this.clientName = name;
	}
	
	public Client() {
		try {
			chatSocket = new Socket("127.0.0.1", 5005);
			InputStreamReader stream = new InputStreamReader(chatSocket.getInputStream());
			reader = new BufferedReader(stream);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Thread readerThread = new Thread(new IncomingReader());
		readerThread.start();

		SetupGui();
	}
	
		
	public void SetupGui() {
		JFrame frame = new JFrame("Chat Client");
		JPanel mainPanel = new JPanel(new BorderLayout());
		outgoing = new JTextField(50);
		messages = new JTextArea(20,50);
		JButton sendButton = new JButton("Send");
		sendButton.addActionListener(new SendButtonListener());
		mainPanel.add(BorderLayout.CENTER, outgoing);
		mainPanel.add(BorderLayout.NORTH, messages);
		mainPanel.add(BorderLayout.SOUTH, sendButton);
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(700,700);
		frame.setVisible(true);
	}
	
	
	public class IncomingReader implements Runnable {
		@Override
		public void run() {
			try {
				String message;
				while ((message = reader.readLine()) != null) {
					messages.append(message + "\n");
				}
			}catch(SocketException socketEx) {
				System.out.println("Connection Lost");
				System.exit(0);
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	
	public class SendButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				System.out.println("Button Pressed");
				PrintWriter writer = new PrintWriter(chatSocket.getOutputStream());
				writer.println(clientName + ": " + outgoing.getText());
				writer.flush();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			outgoing.setText("");
			outgoing.requestFocus();
		}
	}
}
