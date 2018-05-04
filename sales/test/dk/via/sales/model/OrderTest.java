package dk.via.sales.model;

import static org.junit.Assert.*;

import org.junit.*;

public class OrderTest {

	private Order order;
	private Item item3;
	private Item item12;

	@Before
	public void setUp() {
		order = new Order(117, "CHF");
		item3 = new Item(3, "Something", new Money(3, "CHF"));
		item12 = new Item(12, "Something else", new Money(3, "CHF"));
	}
	
	// Zero
	@Test
	public void emptyOrderIsFree() {
		assertEquals(Money.ZERO, order.getPrice());
	}

	@Test
	public void emptyOrderHasNoOrderLines() {
		assertEquals(0, order.getLines().size());
	}
	
	@Test
	public void orderHasTheGivenId() {
		assertEquals(117, order.getId());
	}
	
	@Test
	public void orderHasTheGivenCurrency() {
		assertEquals("CHF", order.getCurrency());
	}
	
	// One
	@Test
	public void orderOfOneItemCostsTheCostOfTheItem() {
		order.add(1, item3);
		assertEquals(item3.getPrice(), order.getPrice());
	}
	
	@Test
	public void multipleCopiesOfSameItemCostsAmountTimesItemCost() {
		order.add(4, item3);
		assertEquals(item3.getPrice().multiply(4), order.getPrice());
	}
	
	@Test
	public void orderOfOneItemHasOneOrderline() {
		order.add(4, item3);
		assertEquals(1, order.getLines().size());
	}
	
	@Test
	public void orderLineFromOrderOfOneItemHasAmountAndItem() {
		order.add(4,  item3);
		OrderLine orderLine = order.getLines().get(0);
		assertEquals(4, orderLine.getAmount());
		assertEquals(item3, orderLine.getItem());
	}
	
	@Test
	public void removingLessThanTheFullAmountOfAnItemRetainsTheOrderLine() {
		order.add(4,  item3);
		order.remove(2, item3);
		assertEquals(1, order.getLines().size());
		assertEquals(2, order.getLines().get(0).getAmount());
	}
	
	// Multiple
	@Test
	public void multipleItemsCostTheSumOfTheItems() {
		order.add(1, item3);
		order.add(4, item12);
		assertEquals(item3.getPrice().add(item12.getPrice().multiply(4)), order.getPrice());
	}
	
	@Test
	public void repeatedAdditionsOfTheSameItemBecomesOneOrderLine() {
		order.add(2, item3);
		order.add(4, item3);
		assertEquals(1, order.getLines().size());
		assertEquals(6, order.getLines().get(0).getAmount());
	}
	
	@Test
	public void removingAllOfAnItemRemovesTheOrderLine() {
		order.add(2, item3);
		order.remove(2, item3);
		assertEquals(0, order.getLines().size());
	}
	
	// Boundary
	@Test(expected = IllegalArgumentException.class)
	public void itsNotAllowedToUseNegativeAmountsToRemoveItems() {
		order.add(2, item3);
		order.add(-1, item3);
	}

	@Test(expected = IllegalStateException.class) 
	public void itsIllegalToRemoveItemsThatWereNeverAdded() {
		order.remove(1, item3);
	}
	
	@Test(expected = IllegalArgumentException.class) 
	public void itsIllegalToRemoveMoreOfAnItemThanWasAdded() {
		order.add(1, item3);
		order.remove(2, item3);
	}
	
	@Test
	public void theOrderCannotBeManipulatedThroughTheOrderLinesList() {
		order.add(3, item3);
		order.getLines().remove(0);
		assertEquals(1, order.getLines().size());
	}
	
	@Test
	public void theOrderCannotBeExtendedThroughTheOrderLines() {
		order.add(3, item3);
		order.getLines().get(0).add(2);
		assertEquals(3, order.getLines().get(0).getAmount());
	}
	
	@Test
	public void theOrderCannotBeReducedThroughTheOrderLines() {
		order.add(3, item3);
		order.getLines().get(0).remove(2);
		assertEquals(3, order.getLines().get(0).getAmount());
	}
	
	// Interface - not here
	
	// Exceptions
	@Test(expected = IllegalArgumentException.class)
	public void itemPricesHaveToBeInTheOrdersCurrency() {
		Order order = new Order(22, "CHF");
		order.add(1, new Item(3, "Something", new Money(1, "USD")));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void amountMustBePositive() {
		order.add(-1, item3);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void removedAmountMustBePositive() {
		order.add(1, item3);
		order.remove(-1, item3);
	}
	
	// Simple Scenarios: The above are all simple
}
