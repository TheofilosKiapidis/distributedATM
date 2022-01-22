import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class serverThread extends Thread{
	private Socket sock;
	private serverProtocol prot;
	
	public serverThread(Socket sock) {
		this.sock = sock;
		prot = new serverProtocol();
	}
	
	public void run() {
		while(true) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			String msg = in.readLine();
			
			//Separate arguments
			ArrayList<String> args = new ArrayList<>();
			String[] temp = msg.split(","); 
			for(int i=0; i<temp.length; i++) { //Messages have the form of Function:id,Ammount:x -> Function:id Ammount:x -> Function id Ammount x
				args.add(temp[i].split(":")[0]);
				args.add(temp[i].split(":")[1]); //There is definitely a better way
			}
			
			switch(args.get(0)) {
			case "Login":
				prot.login(Integer.parseInt( args.get(1)), sock);
				break;
			case "BalanceCheck":
				prot.checkBalance(Integer.parseInt( args.get(1)));
				break;
			case "Retrieve":
				prot.retrieve(Integer.parseInt( args.get(1)), Double.parseDouble( args.get(3)));
				break;
			case "Deposit":
				prot.deposit(Integer.parseInt( args.get(1)), Double.parseDouble( args.get(3)));
				break;	
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		}
	}
}
