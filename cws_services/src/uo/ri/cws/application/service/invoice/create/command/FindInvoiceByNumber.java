package uo.ri.cws.application.service.invoice.create.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.InvoiceRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Invoice;

public class FindInvoiceByNumber implements Command<Optional<InvoiceDto>>{
    private Long number;
    private InvoiceRepository ir = Factory.repository.forInvoice();

    public FindInvoiceByNumber(Long number) {
	this.number = number;
    }

    @Override
    public Optional<InvoiceDto> execute() throws BusinessException {
	Optional<Invoice> invoice = ir.findByNumber(number);
	
	return invoice.map( i -> DtoAssembler.toDto(i));
    }

}
