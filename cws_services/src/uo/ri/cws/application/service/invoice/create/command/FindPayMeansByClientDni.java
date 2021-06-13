package uo.ri.cws.application.service.invoice.create.command;

import java.util.List;
import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.application.service.invoice.InvoicingService.PaymentMeanDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.PaymentMean;

public class FindPayMeansByClientDni implements Command<List<PaymentMeanDto>>{
    private String dni;
    private PaymentMeanRepository pmr = Factory.repository.forPaymentMean();

    public FindPayMeansByClientDni(String dni) {
	ArgumentChecks.isNotNull(dni);
	this.dni = dni;
    }

    @Override
    public List<PaymentMeanDto> execute() {
	List<PaymentMean> paymentMeans = pmr.findPaymentMeansByClientDni(dni);
	
	return DtoAssembler.toPaymentMeanDtoList(paymentMeans);
    }

}
