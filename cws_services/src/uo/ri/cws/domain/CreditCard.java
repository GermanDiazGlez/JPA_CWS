package uo.ri.cws.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import alb.util.assertion.ArgumentChecks;
import alb.util.assertion.StateChecks;

@Entity
@Table(name = "TCREDITCARDS")
public class CreditCard extends PaymentMean {
	@Column(unique = true)private String number;
	private String type;
	private LocalDate validThru;
	
	CreditCard(){}
	
	public CreditCard(String number) {
		super();
		ArgumentChecks.isNotEmpty(number);
		this.number = number;
	}


	public CreditCard(String number, String type, LocalDate validThru) {
		this(number);
		ArgumentChecks.isNotEmpty(type);
		this.type = type;
		this.validThru = validThru;
	}
	
	@Override
	public void pay(double amount) {
		LocalDate ahora = LocalDate.now();
		StateChecks.isTrue(ahora.isBefore(validThru), "La tarjeta no es válida");
		super.pay(amount);
	}


	public String getNumber() {
		return number;
	}


	public String getType() {
		return type;
	}


	public LocalDate getValidThru() {
		return validThru;
	}
	
	@Override
	public boolean canPay(double amount) {
	    return (validThru.compareTo(LocalDate.now()) < 0) ? false : true;
	}	
	
	@Override
	public String toString() {
		return "CreditCard [number=" + number + ", type=" + type 
			+ ", validThru=" + validThru + "]";
	}

}
