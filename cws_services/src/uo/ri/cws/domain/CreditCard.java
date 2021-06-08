package uo.ri.cws.domain;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import alb.util.assertion.ArgumentChecks;

@Entity
@Table(name = "TCREDITCARDS")
public class CreditCard extends PaymentMean {
	@Column(unique = true)private String number;
	private String type;
	private LocalDate validThru;
	
	CreditCard(){}
	
	public CreditCard(String number, String type, LocalDate validThru) {
		ArgumentChecks.isNotEmpty(number);
		ArgumentChecks.isNotEmpty(type);
		ArgumentChecks.isNotNull(validThru);
		
		this.number = number;
		this.type = type;
		this.validThru = validThru;
	}
	
//	public CreditCard(String number) {
//		super();
//		ArgumentChecks.isNotEmpty(number);
//		this.number = number;
//	}
//
//
//	public CreditCard(String number, String type, LocalDate validThru) {
//		this(number);
//		ArgumentChecks.isNotEmpty(type);
//		this.type = type;
//		this.validThru = validThru;
//	}
	
	@Override
	public void pay(double importe) {
		if (validThru.compareTo(LocalDate.now()) < 0)
			throw new IllegalStateException();
		super.pay(importe);
	}
	
	@Override
	public boolean canPay(double amount) {
		if (validThru.compareTo(LocalDate.now()) < 0)
			return false;
		
		return true;
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
	public String toString() {
		return "CreditCard [number=" + number + ", type=" + type 
			+ ", validThru=" + validThru + "]";
	}

}
