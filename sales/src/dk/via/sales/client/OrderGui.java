package dk.via.sales.client;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dk.via.sales.model.Customer;
import dk.via.sales.model.Item;
import dk.via.sales.model.OrderLine;

public class OrderGui extends JFrame implements View {
	private static final long serialVersionUID = 1L;

	private JLabel customerLabel;
	private JList<String> itemList;
	private JList<String> orderLineList;
	private JButton addItemButton;
	private JButton confirmOrderButton;
	private Controller controller;
	
	public OrderGui() {
		super("Order");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		customerLabel = new JLabel();
		itemList = new JList<>();
		orderLineList = new JList<>();
		addItemButton = new JButton("Add item to order");
		confirmOrderButton = new JButton("Confirm order");
		JPanel centerPanel = new JPanel(new GridLayout(1, 2));
		JPanel leftPanel = new JPanel(new BorderLayout());
		JPanel rightPanel = new JPanel(new BorderLayout());
		add(centerPanel, BorderLayout.CENTER);
		centerPanel.add(leftPanel);
		centerPanel.add(rightPanel);
		add(customerLabel, BorderLayout.NORTH);
		leftPanel.add(itemList, BorderLayout.CENTER);
		leftPanel.add(addItemButton, BorderLayout.SOUTH);
		rightPanel.add(orderLineList, BorderLayout.CENTER);
		rightPanel.add(confirmOrderButton, BorderLayout.SOUTH);
		addItemButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.addItemToOrder();
			}
		});
		confirmOrderButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.confirmOrder();
			}
		});
	}
	
	@Override
	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	@Override
	public void displayCustomer(Customer customer) {
		customerLabel.setText(String.format("%s (%s)", customer.getName(), customer.getEmail()));
	}
	
	@Override
	public void displayItems(List<Item> items) {
		ArrayList<String> itemTexts = new ArrayList<>();
		for(Item item: items) {
			itemTexts.add(String.format("%s, %s", item.getName(), item.getPrice().toString()));
		}
		itemList.setModel(new CollectionListModel<String>(itemTexts));
	}
	
	@Override
	public void displayOrderLines(List<OrderLine> lines) {
		ArrayList<String> lineTexts = new ArrayList<>();
		for(OrderLine line: lines) {
			lineTexts.add(String.format("%d %s", line.getAmount(), line.getItem().getName()));
		}
		orderLineList.setModel(new CollectionListModel<String>(lineTexts));
	}

	@Override
	public int getItemIndex() {
		return itemList.getSelectedIndex();
	}

	@Override
	public void displayException(Exception e) {
		JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);
	}
}
