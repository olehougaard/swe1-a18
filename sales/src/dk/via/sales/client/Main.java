package dk.via.sales.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import dk.via.sales.server.SalesModel;

public class Main {
	public static void main(String[] args) throws Exception {
		Registry registry = LocateRegistry.getRegistry(1099);
		SalesModel remoteModel = (SalesModel) registry.lookup("sales");
		Model model = new ModelManager(remoteModel);
		OrderGui view = new OrderGui();
		Controller controller = new Controller(view, model);
		view.setController(controller);
		controller.reset();
		view.setSize(800, 600);
		view.setVisible(true);
	}
}
