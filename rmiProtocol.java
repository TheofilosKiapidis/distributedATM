package project;
import java.rmi.*;
import java.sql.SQLException;

public interface rmiProtocol extends Remote{
 public double getBalance(int id) throws RemoteException, ClassNotFoundException, SQLException;
 public void deposit(int id, double ammount) throws RemoteException, SQLException;
 public boolean withdraw(int id, double ammount) throws RemoteException, SQLException;
}
