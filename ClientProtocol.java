import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientProtocol {
	
	public boolean login(int id) {
		try {
			Socket dataSocket = new Socket("localhost", 1400); //Communication port will be 1400
			OutputStream os = dataSocket.getOutputStream();
			PrintWriter pw = new PrintWriter(os);
			
			pw.println("Login:" + id); //Send login message
			
			ObjectInputStream ois = new ObjectInputStream( dataSocket.getInputStream());
			Response resp = (Response) ois.readObject(); //Retrieve response (check Response class for number indications)
			
			if(resp.ret == 0) {
				System.out.println("Login successful!");
				dataSocket.close();
				return true;
			}
			else if(resp.ret == 1) {
				System.out.println("Error: Wrong ID.");
				dataSocket.close();
				return false;
			}
			else if(resp.ret == 3) {
				System.out.println("Error: Something went wrong! Try again later.");
				dataSocket.close();
				return false;
			}				
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return false; //Hopefully never useful
	}
	
	public void checkBalance(int id) { //CheckBalance -> OK
		try {
			Socket dataSocket = new Socket("localhost", 1400);
			OutputStream os = dataSocket.getOutputStream();
			PrintWriter pw = new PrintWriter(os);
			
			pw.println("BalanceCheck:" + id); //Send balanceCheck message
			
			ObjectInputStream ois = new ObjectInputStream( dataSocket.getInputStream());
			Response resp = (Response) ois.readObject(); //Retrieve response
			
			if(resp.ret == 0) {
				System.out.println("Current balance: " + Integer.parseInt(resp.msg)); //If response OK print account balance from response
				dataSocket.close();
				return;
			}
			else if(resp.ret == 3) {
				System.out.println("Error: Something went wrong! Try again later");
				dataSocket.close();
				return;
			}
			//ID cannot be wrong here since you can check balance only if you have 
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void retrieve(int id, double ammount) { //Retrieve:id,Ammount:ammount
		try {
			Socket dataSocket = new Socket("localhost", 1400);
			OutputStream os = dataSocket.getOutputStream();
			PrintWriter pw = new PrintWriter(os);
			
			pw.println("Retrieve:" + id + ",Ammount:" + ammount);
			
			ObjectInputStream ois = new ObjectInputStream( dataSocket.getInputStream());
			Response resp = (Response) ois.readObject();
			
			if(resp.ret == 0) {
				System.out.println("Done. Retrieved " + ammount + " EUR");
				dataSocket.close();
				return;
			}
			if(resp.ret == 3) {
				System.out.println("Error: Something went wrong! Try again later.");
				dataSocket.close();
				return;
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void deposit(int id, double ammount) { //Deposit:id,Ammount:ammount
		try {
			Socket dataSocket = new Socket("localhost", 1400);
			OutputStream os = dataSocket.getOutputStream();
			PrintWriter pw = new PrintWriter(os);
			
			pw.println("Deposit:" + id + ",Ammount:ammount");
			
			ObjectInputStream ois = new ObjectInputStream( dataSocket.getInputStream());
			Response resp = (Response) ois.readObject();
			
			if(resp.ret == 0) {
				System.out.println("Done. " + ammount + " EUR has been deposited to you account.");
				dataSocket.close();
				return;
			}
			else if(resp.ret == 3) {
				System.out.println("Error: Something went wrong! Try again later.");
				dataSocket.close();
				return;
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private class Response {
		String msg;
		int ret; // 0=OK, 1=Wrong ID, 2=Insuficient balance, 3=Other unknown error
	}
}
