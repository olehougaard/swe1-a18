package dk.via.sales.client;

import java.util.List;

import javax.swing.AbstractListModel;

public class CollectionListModel<T> extends AbstractListModel<T> {
	private static final long serialVersionUID = 1L;

	private List<T> list;
	
	public CollectionListModel(List<T> list) {
		this.list = list;
	}

	@Override
	public int getSize() {
		return list.size();
	}

	@Override
	public T getElementAt(int index) {
		return list.get(index);
	}
}
