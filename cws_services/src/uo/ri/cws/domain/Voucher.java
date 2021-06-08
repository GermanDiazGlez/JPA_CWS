package uo.ri.cws.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import alb.util.assertion.ArgumentChecks;
import alb.util.assertion.StateChecks;

@Entity
@Table(name = "TVOUCHERS")
public class Voucher extends PaymentMean {
	@Column(unique = true)private String code;
	private double available = 0.0;
	private String description;
	
	Voucher() {}
	
	public Voucher(String code, String description) {
		super();
		ArgumentChecks.isNotEmpty(code);
		ArgumentChecks.isNotEmpty(description);
		this.code = code;
		this.description = description;
	    }

	public Voucher(String code, String description, double available) {
		this(code, description);
		ArgumentChecks.isNotNull(available);
		this.available = available;
	}

	/**
	 * Augments the accumulated (super.pay(amount) ) and decrements the available
	 * @throws IllegalStateException if not enough available to pay
	 */
	@Override
	public void pay(double amount) {
		StateChecks.isTrue(available>=amount, "No tienes suficiente dinero");
		super.pay(amount);
		available -= amount;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}	

	public void setAvailable(double available) {
		this.available = available;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getAvailable() {
		return available;
	}

}
