package uo.ri.cws.application.service.invoice.create.command;

import java.util.List;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.WorkOrder;

public class FindWorkOrdersByPlateNumber implements Command<List<InvoicingWorkOrderDto>> {
    private String plate;
    private WorkOrderRepository repo = Factory.repository.forWorkOrder();

    public FindWorkOrdersByPlateNumber(String plate) {
	ArgumentChecks.isNotEmpty( plate );
	this.plate = plate;
    }

    @Override
    public List<InvoicingWorkOrderDto> execute() throws BusinessException {
	List<WorkOrder> workOrders = repo.findByPlateNumber(plate);
	
	return DtoAssembler.toInvoicingWorkOrderDtoList(workOrders);
    }

}
