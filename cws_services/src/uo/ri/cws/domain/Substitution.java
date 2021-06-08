package uo.ri.cws.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;

@Entity
@Table(name = "TSUBSTITUTIONS", uniqueConstraints = {
		@UniqueConstraint(columnNames = {
				"SPAREPART_ID","INTERVENTION_ID"
		})
})
public class Substitution extends BaseEntity{
	// natural attributes
	private int quantity;

	// accidental attributes
	@ManyToOne
	private SparePart sparePart;
	@ManyToOne
	private Intervention intervention;
	
	Substitution() {}

	public Substitution(SparePart sparePart, Intervention intervention, int quantity) {
		super();
		ArgumentChecks.isNotNull(sparePart);
		ArgumentChecks.isNotNull(intervention);
		ArgumentChecks.isTrue(quantity>0);
		Associations.Sustitute.link(sparePart, this, intervention);
		this.quantity = quantity;
	}

	void _setSparePart(SparePart sparePart) {
		this.sparePart = sparePart;
	}

	void _setIntervention(Intervention intervention) {
		this.intervention = intervention;
	}

	@Override
	public String toString() {
		return "Substitution [quantity=" + quantity + ", sparePart=" + sparePart + ", intervention=" + intervention
				+ "]";
	}

	public int getQuantity() {
		return quantity;
	}

	public SparePart getSparePart() {
		return sparePart;
	}

	public Intervention getIntervention() {
		return intervention;
	}

	public double getAmount() {
		double amount = 0.0;
		amount = sparePart.getPrice() * quantity;
		return amount;
	}

}
