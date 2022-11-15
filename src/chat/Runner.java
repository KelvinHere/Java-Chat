package Chat;

public class Runner {
	public static void main(String[] args) {
		if (args.length > 0 && args[0].equals("Server")) {
			System.out.println("Server started");
			Server s1 = new Server();
			s1.go();
		} else {
			System.out.println("Client started");
			Client c1 = new Client();
			c1.receive();
		}
	}
}
