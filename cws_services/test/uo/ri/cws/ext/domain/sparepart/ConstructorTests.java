package uo.ri.cws.ext.domain.sparepart;

import org.junit.Before;
import org.junit.Test;

import uo.ri.cws.domain.SparePart;

public class ConstructorTests {

	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Spare part code cannot be null
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testCodeNull() {
		new SparePart(null);
	}

	/**
	 * Spare part code cannot be empty
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testCodeEmpty() {
		new SparePart( "" );
	}

	/**
	 * Spare part description cannot be null
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testDescriptionCannotBeNull() {
		new SparePart( "code", null, 0, 0, 0, 0 );
	}

	/**
	 * Spare part description cannot be empty
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testDescriptionCannotBeEmpty() {
		new SparePart( "code", "", 0, 0, 0, 0 );
	}

	/**
	 * Spare part price cannot be negative
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testPriceCannotBeNgative() {
		new SparePart( "code", "description", -1.0, 0, 0, 0 );
	}

	/**
	 * Spare part code stock cannot be negative
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testStockCannotBeNgative() {
		new SparePart( "code", "description", 0.0, -1, 0, 0 );
	}

	/**
	 * Spare part code minStock cannot be negative
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testMinStockCannotBeNgative() {
		new SparePart( "code", "description", 0.0, 0, -1, 0 );
	}

	/**
	 * Spare part code maxStock cannot be negative
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testMaxStockCannotBeNgative() {
		new SparePart( "code", "description", 0.0, 0, 0, -1 );
	}
}
