package dk.via.sales.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import dk.via.sales.data.Persistence;

public class StartServer {
	public static void main(String[] args) throws Exception {
		Registry registry = LocateRegistry.createRegistry(1099);
		registry.bind("sales", new SalesModelManager(new Persistence()));
	}
}
