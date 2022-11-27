package project;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;

public class protocolImplementation extends UnicastRemoteObject implements rmiProtocol {
  private final Connection con;
  protected protocolImplementation() throws RemoteException, ClassNotFoundException, SQLException {
    Class.forName("com.mysql.cj.jdbc.Driver");
    this.con = DriverManager.getConnection("jdbc:mysql://localhost/atm", "root", "teok2000");
  }

  public double getBalance(int id) throws SQLException {
    Statement st = con.createStatement();
    ResultSet rs = st.executeQuery("SELECT balance " +
                                        "FROM accounts " +
                                        "WHERE id=" + id + ";");

    if(rs.next()){
      return rs.getDouble("balance");
    }
    return 0;
  }  

  public void deposit(int id, double amount) throws SQLException {
    Statement st = con.createStatement();
    st.execute("UPDATE accounts "+
                          "SET balance = balance+"+ amount +
                          " WHERE id = " + id);
  }

  public boolean withdraw(int id, double amount) throws SQLException {
    double balance = getBalance(id);
    if(balance<amount){
      return false;
    }
    Statement st = con.createStatement();
    st.execute("UPDATE accounts " +
                        "SET balance = balance-"+amount+
                        " WHERE id = "+id);
    return true;
  }
}
