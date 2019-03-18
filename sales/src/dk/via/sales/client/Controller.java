package dk.via.sales.client;

import java.rmi.RemoteException;

import dk.via.sales.model.Item;

public class Controller {
	private View view;
	private Model model;

	public Controller(View view, Model model) {
		this.view = view;
		this.model = model;
	}

	public void reset() {
		view.displayItems(model.getItems());
		view.displayCustomer(model.getCustomer());
	}

	public void addItemToOrder() {
		int itemIndex = view.getItemIndex();
		if (itemIndex >= 0) {
			Item item = model.getItems().get(itemIndex);
			model.addItem(item);
			view.displayOrderLines(model.getOrderLines());
		}
	}

	public void confirmOrder() {
		if (model.getOrderLines().size() > 0) {
			try {
				model.save();
				model.clear();
				view.displayItems(model.getItems());
				view.displayOrderLines(model.getOrderLines());
			} catch (RemoteException e) {
				view.displayException(e);
			}
		}
	}
}
