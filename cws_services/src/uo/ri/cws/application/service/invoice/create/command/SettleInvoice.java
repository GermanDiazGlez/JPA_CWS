package uo.ri.cws.application.service.invoice.create.command;

import java.util.Map;
import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ChargeRepository;
import uo.ri.cws.application.repository.InvoiceRepository;
import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Charge;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.domain.Invoice.InvoiceStatus;
import uo.ri.cws.domain.PaymentMean;

public class SettleInvoice implements Command<Void>{
    private String invoiceId;
    private Map<String, Double> charges;
	
    ChargeRepository cr = Factory.repository.forCharge();
    InvoiceRepository  ir = Factory.repository.forInvoice();
    PaymentMeanRepository pmr = Factory.repository.forPaymentMean();

    public SettleInvoice(String invoiceId, Map<String, Double> charges) {
	ArgumentChecks.isNotEmpty(invoiceId);
	ArgumentChecks.isNotNull( charges );
	
	this.invoiceId = invoiceId;
	this.charges = charges;
    }

    @Override
    public Void execute() throws BusinessException {
	Optional<Invoice> invoice = ir.findById(invoiceId);
	BusinessChecks.exists(invoice, "There is not an invoice with that id");
	BusinessChecks.isTrue(invoice.get().getStatus() != InvoiceStatus.PAID, "The invoice is already PAID");
	
	checkPaymentMeans(invoice.get(), charges);
	
	for (String key : charges.keySet()) {
		Charge c = new Charge(invoice.get(), pmr.findById(key).get(), charges.get(key));
		cr.add(c);
	}
	
	invoice.get().settle();
	
	return null;
    }
    
    private void checkPaymentMeans(Invoice invoice, Map<String, Double> charges) throws BusinessException {
	double totalPrice = 0;
	for (String key : charges.keySet()) {
		Optional<PaymentMean> pm = pmr.findById(key);
		BusinessChecks.exists(pm, "There is not a payment mean with the id: " + key);

		double invoicePrice = charges.get(key);
		BusinessChecks.isTrue(pm.get().canPay(invoicePrice), "You cant pay the invoices, check your Payment Mean");
		totalPrice += invoicePrice;
	}
	BusinessChecks.isTrue( Math.abs( totalPrice - invoice.getAmount()) <= 0.01, "The amount from the invoice and the total of all charges dont match");
    }

}
