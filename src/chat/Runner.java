package Chat;

public class Runner {
	public static void main(String[] args) {
		if (args.length > 0) {
			// If args are for client
			if (args[0].equals("Client")) {
				System.out.println("Client started");
				Client c1 = new Client(args[1]);
			}
			// If args are for server
			if (args[0].equals("CommandLineServer")) {
				System.out.println("Command line server started");
				Server s1 = new Server();
				s1.go();
			}			
		} else {
			// Default to GUI server
			System.out.println("GUI server started");
			ServerGUI s1gui = new ServerGUI();			
		}
	}
}
