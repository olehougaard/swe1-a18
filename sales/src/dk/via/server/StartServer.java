package dk.via.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class StartServer {
	public static void main(String[] args) throws Exception {
		Registry registry = LocateRegistry.createRegistry(1099);
		registry.bind("sales", new SalesModelManager());
	}
}
