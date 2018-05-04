package dk.via.money;

import static org.junit.Assert.*;

import org.junit.*;

public class MoneyTest {

	private Money chf12;
	private Money chf24;
	private Money usd12;
	
	@Before
	public void setUp() {
		chf12 = new Money(12, "CHF");
		chf24 = new Money(24, "CHF");
		usd12 = new Money(12, "USD");
	}

	@Test
	public void getCurrencyGetsTheCurrencyFromTheConstruction() {
		assertEquals("CHF", chf12.getCurrency());
	}

	@Test
	public void getAmountGetsTheAmountFromTheConstruction() {
		assertEquals(12, chf12.getAmount(), 0.0001);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void currencyIsRequired() {
		new Money(12, null);
	}
	
	@Test
	public void negativeAmountsArePermitted() {
		assertEquals(chf12, new Money(-12, "CHF").multiply(-1));
	}
	
	@Test
	public void moneyIsEqualWhenCurrencyAndAmountIs() {
		assertTrue(chf12.equals(new Money(12, "CHF")));
	}
	
	@Test
	public void moneyWithDifferentAmountsAreDifferent() {
		assertFalse(chf12.equals(chf24));
	}
	
	@Test
	public void moneyWithDifferentCurrenciesAreDifferent() {
		assertFalse(chf12.equals(usd12));
	}
	
	@Test
	public void moneyDoesntEqualNonMoney() {
		assertFalse(chf12.equals("12.00 CHF"));
	}
	
	@Test
	public void additionAddsTheAmounts() {
		assertEquals(new Money(36, "CHF"), chf12.add(chf24));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void additionRequiresEqualCurrencies() {
		chf12.add(usd12);
	}
	
	@Test
	public void equalMoneyHasEqualHashCode() {
		assertEquals(chf12.hashCode(), new Money(12, "CHF").hashCode());
	}
	
	@Test
	public void multiplyMultipliesAmount() {
		assertEquals(chf24, chf12.multiply(2));
	}
	
	@Test
	public void toStringShowsAmountToTwoSpacesSpaceCurrency() {
		assertEquals("12.00 CHF", chf12.toString());
	}
}
