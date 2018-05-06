package dk.via.sales.client;

public class Main {
	public static void main(String[] args) throws Exception {
		ModelManager model = new ModelManager();
		OrderGui view = new OrderGui();
		Controller controller = new Controller(view, model);
		view.setSize(800, 600);
		view.setVisible(true);
	}
}
