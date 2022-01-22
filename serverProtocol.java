import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class serverProtocol {
	private Connection con = null;
	private Statement st = null;
	private ResultSet rs = null;
	private Socket sock = null;
	
	
	public void login(int id, Socket sock) throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver"); //Load driver
			
			con = DriverManager.getConnection("jdbc:mysql://localhost/atm", "root", ""); //Login as root
			this.sock = sock;
			st = con.createStatement();
			
			ResultSet rs = st.executeQuery("SELECT id "
					+ "FROM accounts JOIN users ON accounts.id=users.id "
					+ "WHERE id=" + id);
			
			OutputStream os = sock.getOutputStream();
			ObjectOutputStream oos= new ObjectOutputStream(os);
			
			
			Response resp = new Response();
			resp.msg = "";
			
			if(rs.next()) { //If it exists
				resp.ret = 0;
				
				oos.writeObject(resp);
			}
			else {
				resp.ret = 1;
				
				oos.writeObject(resp);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//The rest of the protocol is executed after loging in so no need to reinstantiate
	public void checkBalance(int id) {
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT balance"
					+ "FROM accounts"
					+ "WHERE id=" + id);
			
			OutputStream os = sock.getOutputStream();
			ObjectOutputStream oos= new ObjectOutputStream(os);
			
			Response resp = new Response();
			resp.msg = "";
			
			if(rs.next()) { //If it exists
				resp.ret = 0;
				resp.msg = "" + rs.getDouble("balance");
				oos.writeObject(resp);
			}
			else {
				resp.ret = 3;
				
				oos.writeObject(resp);
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void retrieve(int id, double ammount) {
		try{
			st = con.createStatement();
			rs = st.executeQuery("SELECT balance"
				+ "FROM accounts"
				+ "WHERE id=" + id);
			
			OutputStream os = sock.getOutputStream();
			ObjectOutputStream oos= new ObjectOutputStream(os);
			
			Response resp = new Response();
			
			double balance = 0;
			
			if(rs.next()) {
				balance = rs.getDouble("balance");
			}
			else {
				resp.msg = "";
				resp.ret = 3;
				
				oos.writeObject(resp);
			}
			
			if(balance<ammount) {
				resp.msg = "";
				resp.ret = 2;
				oos.writeObject(resp);
			}
			else {
				st = con.createStatement();
				rs = st.executeQuery("UPDATE accounts"
						+ "SET balance=balance-" + ammount
						+ "WHERE id=" + id);
				
				resp.msg = "";
				resp.ret = 0;
				oos.writeObject(resp);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deposit(int id, double ammount) {
		try {
			st = con.createStatement();
			st.executeQuery("UPDATE accounts"
					+ "SET balance=balance+" + ammount
					+ "WHERE id=" + id);
			
			Response resp = new Response();
			resp.msg = "";
			resp.ret = 0;
			
			OutputStream os = sock.getOutputStream();
			ObjectOutputStream oos= new ObjectOutputStream(os);
			
			oos.writeObject(resp);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private class Response implements Serializable {
		String msg;
		int ret; // 0=OK, 1=Wrong ID, 2=Insuficient balance, 3=Other unknown error
	}
}
