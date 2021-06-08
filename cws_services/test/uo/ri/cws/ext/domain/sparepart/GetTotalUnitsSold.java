package uo.ri.cws.ext.domain.sparepart;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import uo.ri.cws.domain.Intervention;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.SparePart;
import uo.ri.cws.domain.Substitution;
import uo.ri.cws.domain.Vehicle;
import uo.ri.cws.domain.WorkOrder;

public class GetTotalUnitsSold {

	private Vehicle vehicle;
	private SparePart sp1;
	private Mechanic m1;
	private Mechanic m2;
	private WorkOrder wo1;
	private WorkOrder wo2;

	@Before
	public void setUp() throws Exception {
		sp1 = new SparePart("sp1");
		m1 = new Mechanic("123");
		m2 = new Mechanic("234");
		vehicle = new Vehicle("1111AAA");
		wo1 = new WorkOrder(vehicle);
		wo2 = new WorkOrder(vehicle);
	}

	/**
	 * Not used spare part has 0 units sold
	 */
	@Test
	public void testNotUsedZero() {

		int qty = sp1.getTotalUnitsSold();

		assertEquals(0, qty);
	}

	/**
	 * Used in several interventions get the right accumulated value
	 */
	@Test
	public void testSeveralInterventionsUsed() {
		Intervention i = new Intervention(m1, wo1, 60);
		new Substitution(sp1, i, 10);

		i = new Intervention(m2, wo2, 60);
		new Substitution(sp1, i, 1);

		int qty = sp1.getTotalUnitsSold();

		assertEquals( 11, qty);
	}

}
