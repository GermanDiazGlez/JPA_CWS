package uo.ri.cws.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import alb.util.assertion.ArgumentChecks;

@Entity
@Table(name = "TCASHES")
public class Cash extends PaymentMean {
	
	public Cash() {}
	
	public Cash(Client client) {
		ArgumentChecks.isNotNull(client);
		Associations.Pay.link(this, client);
	}

}
