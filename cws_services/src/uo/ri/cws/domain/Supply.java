package uo.ri.cws.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;

@Entity
@Table(name = "TSUPPLIES", uniqueConstraints = {
		@UniqueConstraint(columnNames = {
				"SPAREPART_ID","PROVIDER_ID"
		})
})
public class Supply extends BaseEntity{
	
	private double price;
	private int deliveryTerm;
	
	// accidental attributes
	@ManyToOne
	private SparePart sparePart;
	@ManyToOne
	private Provider provider;
	
	public Supply() {}
	
	public Supply(Provider provider, SparePart sparePart, double price, int deliveryTerm) {
		ArgumentChecks.isNotNull(provider);
		ArgumentChecks.isNotNull(sparePart);
		ArgumentChecks.isNotNull(price);
		ArgumentChecks.isNotNull(deliveryTerm);
		this.price = price;
		this.deliveryTerm = deliveryTerm;
		this.sparePart = sparePart;
		this.provider = provider;
		Associations.Supplies.link(sparePart, this, provider);
	}

	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getDeliveryTerm() {
		return deliveryTerm;
	}
	public void setDeliveryTerm(int deliveryTerm) {
		this.deliveryTerm = deliveryTerm;
	}
	public SparePart getSparePart() {
		return sparePart;
	}
	void _setSparePart(SparePart sparePart) {
		this.sparePart = sparePart;
	}
	public Provider getProvider() {
		return provider;
	}
	void _setProvider(Provider provider) {
		this.provider = provider;
	}
	
	
	
}
