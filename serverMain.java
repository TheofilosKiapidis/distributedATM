import java.net.ServerSocket;
import java.net.Socket;

public class serverMain {

	public static void main(String[] args) {
		try {
			ServerSocket ss = new ServerSocket(1400);
			while(true) {
				Socket client = ss.accept();
				serverThread st = new serverThread(client);
				
				st.start();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
