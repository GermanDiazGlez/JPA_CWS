package uo.ri.cws.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.Invoice.InvoiceStatus;
import uo.ri.cws.domain.base.BaseEntity;

@Entity
@Table(name = "TCHARGES",uniqueConstraints = {
		@UniqueConstraint(columnNames = {
				"INVOICE_ID","PAYMENTMEAN_ID"
		})
})
public class Charge extends BaseEntity {
	// natural attributes
	private double amount = 0.0;

	// accidental attributes
	@ManyToOne
	private Invoice invoice;
	@ManyToOne
	private PaymentMean paymentMean;
	
	Charge() {}

	public Charge(Invoice invoice, PaymentMean paymentMean, double amount) {
		ArgumentChecks.isTrue(amount >= 0);
		ArgumentChecks.isNotNull(invoice);
		ArgumentChecks.isNotNull(paymentMean);
		this.amount = amount;
		
		// increment the paymentMean accumulated -> paymentMean.pay( amount )
		paymentMean.pay(amount);
		// link invoice, this and paymentMean
		Associations.Charges.link(paymentMean, this, invoice);
		
	}

	/**
	 * Unlinks this charge and restores the accumulated to the payment mean
	 * @throws IllegalStateException if the invoice is already settled
	 */
	public void rewind() {
		// asserts the invoice is not in PAID status
		ArgumentChecks.isTrue(!this.getInvoice().getStatus().equals(InvoiceStatus.PAID));
		// decrements the payment mean accumulated ( paymentMean.pay( -amount) )
		this.getPaymentMean().pay(-amount);
		// unlinks invoice, this and paymentMean
		Associations.Charges.unlink(this);
	}

	void _setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	void _setPaymentMean(PaymentMean paymentMean) {
		this.paymentMean = paymentMean;
	}

	public double getAmount() {
		return amount;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public PaymentMean getPaymentMean() {
		return paymentMean;
	}

	@Override
	public String toString() {
		return "Charge [amount=" + amount + ", invoice=" + invoice + ", paymentMean=" + paymentMean + "]";
	}
	
}
