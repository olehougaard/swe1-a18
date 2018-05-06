package dk.via.sales.client;

import java.rmi.RemoteException;

import dk.via.sales.model.Item;

public class Controller {
	private OrderGui view;
	private ModelManager model;
	
	public Controller(OrderGui view, ModelManager model) {
		this.view = view;
		this.model = model;
		view.setController(this);
		view.setItems(model.getItems());
	}
	
	public void addItemToOrder() {
		int itemIndex = view.getItemIndex();
		if (itemIndex >= 0) {
			Item item = model.getItems().get(itemIndex);
			model.addItem(item);
			view.setOrderLines(model.getOrderLines());
		}
	}
	
	public void confirmOrder() {
		try {
			model.save();
			model.clear();
			view.setItems(model.getItems());
			view.setOrderLines(model.getOrderLines());
		} catch (RemoteException e) {
			view.displayException(e);
		}
	}
}
