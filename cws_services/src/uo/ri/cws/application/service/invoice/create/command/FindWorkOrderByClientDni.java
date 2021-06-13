package uo.ri.cws.application.service.invoice.create.command;

import java.util.List;
import uo.ri.conf.Factory;
import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.WorkOrder;

public class FindWorkOrderByClientDni implements Command<List<InvoicingWorkOrderDto>>{
    private String dni;
    private WorkOrderRepository wor = Factory.repository.forWorkOrder();
    
    public FindWorkOrderByClientDni(String dni) {
	ArgumentChecks.isNotEmpty( dni );
	this.dni = dni;
    }

    @Override
    public List<InvoicingWorkOrderDto> execute() {
	List<WorkOrder> lw = wor.findByClientDni(dni);
	
	return DtoAssembler.toInvoicingWorkOrderDtoList(lw);
    }

}
