package Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Client {
	private static int clientCount = 0;
	Socket chatSocket;
	BufferedReader reader;
	JTextField outgoing;

	
	public Client() {
		clientCount ++;
		try {
			chatSocket = new Socket("127.0.0.1", 5000);
			InputStreamReader stream = new InputStreamReader(chatSocket.getInputStream());
			reader = new BufferedReader(stream);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		SetupGui();
	}
	
	public void SetupGui() {
		JFrame frame = new JFrame("ChatClient No:" + clientCount);
		JPanel mainPanel = new JPanel();
		outgoing = new JTextField(50);
		JButton sendButton = new JButton("Send");
		sendButton.addActionListener(new SendButtonListener());
		mainPanel.add(outgoing);
		mainPanel.add(sendButton);
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(700,300);
		frame.setVisible(true);
	}
	
	
	public void receive() {
		try {
			System.out.println("Recieved msg : " + reader.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public class SendButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				System.out.println("Button Pressed");
				PrintWriter writer = new PrintWriter(chatSocket.getOutputStream());
				writer.println("Client " + clientCount + "'s Message: " + outgoing.getText());
				writer.flush();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
