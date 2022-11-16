package Chat;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ServerGUI {
	int portNumber = 5000;
	JTextArea messageArea = new JTextArea(30,30);
	ServerGUI selfRef = this;
	JLabel a = new JLabel("ab");
	
	
	public ServerGUI() {
		setUpGui();
	}
		
	
	private void setUpGui() {
		JFrame frame = new JFrame("Chat Server");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		
		JPanel portPanel = new JPanel();
		portPanel.setLayout(new FlowLayout());
		JLabel portLabel = new JLabel("Port");
		JTextField portField = new JTextField(5);
		portField.setText(String.valueOf(portNumber));
		JButton startServerButton = new JButton("Start Server");
		startServerButton.addActionListener(new StartServerListener());
		messageArea = new JTextArea(20,50);
		
		portPanel.add(portLabel);
		portPanel.add(portField);
		portPanel.add(startServerButton);
		mainPanel.add(BorderLayout.SOUTH, portPanel);
		mainPanel.add(BorderLayout.CENTER, messageArea);
		mainPanel.add(BorderLayout.NORTH, a);
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(700,700);
		frame.setVisible(true);
	}
	
	public void addMessage(String message) {
		messageArea.append("\n" + message);
	}
	
	private class StartServerListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Server started on port " + portNumber);
			Thread t = new Thread(new Server(portNumber, selfRef));
			t.start();
		}
		
	}
}
