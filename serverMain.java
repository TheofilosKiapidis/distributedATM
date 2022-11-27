package project;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.Scanner;

public class serverMain {
    public static void main(String[] args) throws SQLException, RemoteException, ClassNotFoundException {
        System.setProperty("java.rmi.server.hostname", "localhost");

        rmiProtocol prot = new protocolImplementation();
        Registry reg = LocateRegistry.createRegistry(1099);
        reg.rebind("rmiProtocol", prot);

        //Waits until we press enter
        Scanner in = new Scanner(System.in);
        in.nextLine();
    }
}
