package uo.ri.cws.ext.domain.sparepart;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import uo.ri.cws.domain.SparePart;

public class UpdatePriceAndStockTests {

	private SparePart spare;

	@Before
	public void setUp() throws Exception {
		double price = 10.0;
		int stock = 4;
		int minStock = 5;
		int maxStock = 10;
		spare = new SparePart("sp1", "desc", price, stock, minStock, maxStock);
	}

	/**
	 * new price and stock is computed following the specification
	 */
	@Test
	public void testSimpleComputation() {
		int newQuantity = 6;
		double purchasePrice = 100.0;
		int expectedStock = spare.getStock() + newQuantity;
		double expectedPrice = (
					spare.getStock() * spare.getPrice()
					+ newQuantity * purchasePrice * 1.2
				)
				/ expectedStock;

		spare.updatePriceAndStock(purchasePrice, newQuantity);

		assertEquals( expectedStock, spare.getStock() );
		assertEquals( expectedPrice, spare.getPrice(), 0.0001);
	}

	/**
	 * new quantity must be over zero
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testQuantityMustBeOverZero() {
		int QUANTITY_ZERO = 0;

		spare.updatePriceAndStock(100.0, QUANTITY_ZERO);
	}

	/**
	 * purchase price cannot be negative
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testPurchasePriceCannotBeNegative() {
		double purchasePrice = -1.0;

		spare.updatePriceAndStock(purchasePrice, 10);
	}

	/**
	 * update quantity even over max stock
	 * (is not the responsibility of spare part to check this)
	 */
	@Test
	public void testComputationOverMaxStock() {
		int newQuantity = 100;
		double purchasePrice = 100.0;
		int expectedStock = spare.getStock() + newQuantity;
		double expectedPrice = (
					spare.getStock() * spare.getPrice()
					+ newQuantity * purchasePrice * 1.2
				)
				/ expectedStock;

		spare.updatePriceAndStock(purchasePrice, newQuantity);

		assertEquals( expectedStock, spare.getStock() );
		assertEquals( expectedPrice, spare.getPrice(), 0.0001);
	}


}
