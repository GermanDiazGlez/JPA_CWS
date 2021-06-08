package uo.ri.cws.ext.domain.sparepart;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import uo.ri.cws.domain.SparePart;

public class GetQuantityToOrderTests {

	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Compute 0 parts to order if stock equals to min stock
	 */
	@Test
	public void testOverStockMinStock() {
		SparePart sp = new SparePart("sp1", "desc", 1.0, 5, 5, 10);

		assertEquals( 0, sp.getQuantityToOrder() );
	}

	/**
	 * Compute 0 parts to order if stock equals to max stock
	 */
	@Test
	public void testOverStockMaxStock() {
		SparePart sp = new SparePart("sp1", "desc", 1.0, 10, 5, 10);

		assertEquals( 0, sp.getQuantityToOrder() );
	}

	/**
	 * Compute 0 parts to order if stock over max stock
	 */
	@Test
	public void testOverStockOverMaxStock() {
		SparePart sp = new SparePart("sp1", "desc", 1.0, 15, 5, 10);

		assertEquals( 0, sp.getQuantityToOrder() );
	}

	/**
	 * Compute 0 parts to order if stock equals to max stock
	 */
	@Test
	public void testOverStockOtherStock() {
		SparePart sp = new SparePart("sp1", "desc", 1.0, 8, 5, 10);

		assertEquals( 0, sp.getQuantityToOrder() );
	}

	/**
	 * Compute how many parts to order for under stock 0
	 */
	@Test
	public void testUnderStockZero() {
		SparePart sp = new SparePart("sp1", "desc", 1.0, 0, 5, 10);

		assertEquals( 10, sp.getQuantityToOrder() );
	}

	/**
	 * Compute how many parts to order for under stock min stock - 1
	 */
	@Test
	public void testUnderStockMinStock() {
		SparePart sp = new SparePart("sp1", "desc", 1.0, 4, 5, 10);

		assertEquals( 6, sp.getQuantityToOrder() );
	}

}
