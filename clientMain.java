import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class clientMain {

	public static void main(String[] args) {
		ClientProtocol cp = new ClientProtocol();
		
		//Login phase
		Card card = login();
		if(card == null || !cp.login(card.id)) { //if fail to type correct pin or server error-> quit
			System.out.println("Failled to login. Ending Session.");
			System.exit(-1); 
		}
		
		//Menu loop
		while(true) {
			System.out.println("Select an action:\n"
					+ "1. Check Balance"
					+ "2. Retrieve"
					+ "3. Deposit"
					+ "4. Exit");
			
			Scanner in = new Scanner(System.in);
			System.out.print("Select: ");
			int input = in.nextInt();
			
			if(input == 1) { //Balance
				cp.checkBalance(card.id);
			}
			else if (input == 2){
				System.out.print("Ammount to recieve: ");
				cp.retrieve(card.id, in.nextDouble());
			}
			else if (input == 3) {
				System.out.print("Ammount to deposit: ");
				cp.deposit(card.id, in.nextDouble());
			}
			else if (input == 4) {
				System.exit(0);
			}
			else {
				System.out.println("Invalid input. Try again.");
			}
			
		}
		
		
	}
	
	private static Card login() { //
		try { //Get demo cards from their folder
			File cardFolder = new File("./cards");
			String[] cardNames = cardFolder.list();
			ArrayList<Card> cards = new ArrayList<>();			
			
			for(int i = 0; i<cardNames.length; i++) {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./cards/" + cardNames[i]));
				cards.add((Card) ois.readObject());
			}
			
			int input;
			Scanner in = new Scanner(System.in);
			while(true) {//Card selection
				System.out.println("Choose your card:");
				for(int i=0; i<cards.size(); i++) { //Print cards
					System.out.println((i+1) + ". " + cardNames[i]);
				}
				
				System.out.print("Choose: ");
				input = in.nextInt(); //Read input
				
				if( !(input<0 || input>cards.size()) ) { //If input within card range leave
					 break;
				}
				System.out.println("Give apropriate number to select your card");
			}
			in.nextLine();
			String pin = cards.get(input-1).pin;
			for(int i =0; i<3; i++) { //Up to 3 tries
				System.out.print("Pin: ");
				String inPin = in.nextLine();
				in.nextLine();
				if(inPin == pin) {
					System.out.println("Logged in!");
					return cards.get(input-1);
				}
				else if(i==2) {//If last try doesnt return -> give null
					return null;
				}
			}						
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null; //For compiler error
	}
}
