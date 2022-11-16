package Chat;

public class Runner {
	public static void main(String[] args) {
		if (args.length > 0 && args[0].equals("Client")) {
			System.out.println("Client started");
			if (args.length == 2) {
				Client c1 = new Client(args[1]);
			} else {
				Client c1 = new Client();
			}
			
			
		} else {
			System.out.println("Server started");
			Server s1 = new Server();
			s1.go();
		}
	}
}
