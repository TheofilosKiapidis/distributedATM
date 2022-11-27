package project;
import java.io.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class clientMain {
  public static void main(String[] args) throws RemoteException, NotBoundException, SQLException, ClassNotFoundException {
    //Find registry
   Registry reg = LocateRegistry.getRegistry("localhost", 1099);
   rmiProtocol ref = (rmiProtocol) reg.lookup("rmiProtocol");

   ArrayList<Card> cardList = new ArrayList<>();

   //Load the cards
      try {
        for(int i=1; i<=3; i++){
            FileInputStream fis = new FileInputStream("card" + i + ".ser");
            ObjectInputStream oos = new ObjectInputStream(fis);
            cardList.add((Card)oos.readObject());
            oos.close();
        }
      } catch (FileNotFoundException e) {
          e.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      } catch (ClassNotFoundException e) {
          e.printStackTrace();
      }

      boolean loggedIn = false;
      int userId = -1; //Required initialization
      //Login phase
      while(!loggedIn){
        System.out.println("Pick a card:\n" +
                           "1. c1\n" +
                            "2. c2\n" +
                            "3. c3");
        Scanner in = new Scanner(System.in);
        int input = in.nextInt();
        in.nextLine(); // Clears read buffer

        if(input>0 && input<4){
            String pin = in.nextLine();
            if(pin.equals(cardList.get(input -1).getPin())){
                System.out.println("Succesfully logged in!");
                loggedIn = true;
                userId = cardList.get(input -1).getId();
            }
            else{
                System.out.println("Wrong pin. Try again.");
            }
        }
        else {
            System.out.println("Wrong input.");
        }

      }


    //Menu loop
    while(true){
      System.out.println("Select an Action:\n"
                       + "1. Check Balance\n"
                       + "2. Withdraw\n"
                       + "3. Deposit\n"
                       + "4. Exit\n");

      Scanner in = new Scanner(System.in);
      int input = in.nextInt();

      if(input == 1){
        System.out.println("Balance: " + ref.getBalance(userId));
      }
      else if (input == 2){
        System.out.print("Enter an ammount (decimals seperated by comma):");
        double ammount = in.nextDouble();
        if(ref.withdraw(userId, ammount)){
            System.out.println("Withdrawal successful!");
        }
        else {
            System.out.println("Not enough balance!");
        }
      }
      else if (input == 3){
        System.out.print("Enter ammount (decimals seperated by comma):");
        double ammount = in.nextDouble();
        ref.deposit(userId, ammount);
        System.out.println("Deposit successful!");
      }
      else if (input == 4){
          break;
      }
      else {
          System.out.println("Wrong input.");
      }
    }
  }
}
