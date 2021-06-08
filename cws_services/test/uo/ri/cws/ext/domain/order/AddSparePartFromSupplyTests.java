package uo.ri.cws.ext.domain.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import uo.ri.cws.domain.Associations;
import uo.ri.cws.domain.Order;
import uo.ri.cws.domain.OrderLine;
import uo.ri.cws.domain.Provider;
import uo.ri.cws.domain.SparePart;
import uo.ri.cws.domain.Supply;

public class AddSparePartFromSupplyTests {

	private Provider p1;
	private SparePart sp1;
	private Supply s;
	private Order order;

	@Before
	public void setUp() throws Exception {
		p1 = new Provider("P1");
		sp1 = new SparePart("sp1");
		s = new Supply(p1, sp1, 10.0, 5);
		order = new Order("o1");
		Associations.Deliver.link(p1, order);

		sp1.setMinStock( 5 );
		sp1.setMaxStock( 10 );
	}

	/**
	 * A null supply raises exception
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testNullSupply() {
		order.addSparePartFromSupply( null );
	}

	/**
	 * Add for a spare with stock equals to min stock
	 */
	@Test
	public void testAddOverStockSupply() {
		sp1.setStock( sp1.getMinStock() );

		order.addSparePartFromSupply( s );

		assertTrue( order.getOrderLines().isEmpty() );
	}

	/**
	 * Add for a spare under stock
	 */
	@Test
	public void testAddUnderStockSupply() {
		sp1.setStock( sp1.getMinStock() - 1 );

		order.addSparePartFromSupply( s );

		assertEquals(1 , order.getOrderLines().size());
		OrderLine ol = order.getOrderLines().iterator().next();

		assertEquals( sp1, ol.getSparePart() );
		assertEquals( 6, ol.getQuantity() );
		assertEquals( 10.0, ol.getPrice(), 0.001 );
		assertEquals( 60.0, ol.getAmount(), 0.001 );
		assertEquals( 60.0, order.getAmount(), 0.001 );
	}

	/**
	 * Add for a spare that is already added (same supply) to the order throws exception
	 */
	@Test(expected=IllegalStateException.class)
	public void testAddSpareFromRepeatSupply() {
		sp1.setStock( 4 );
		order.addSparePartFromSupply( s );

		order.addSparePartFromSupply( s );
	}

	/**
	 * Add for a spare from other provider that is already added to the order
	 */
	@Test(expected=IllegalStateException.class)
	public void testAddRepeatedSpareFromDifferentSupply() {
		Provider p2 = new Provider("P2");
		Supply s2 = new Supply( p2, sp1, 100, 4 );
		sp1.setStock( 4 );
		try {
			order.addSparePartFromSupply( s );
		} catch(IllegalStateException e) {
			fail("Exception aholuld not be thrown here...");
		}

		order.addSparePartFromSupply( s2 ); // ...but here
	}

}
