package uo.ri.cws.application.service.invoice.create.command;

import java.util.List;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.InvoiceRepository;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.domain.WorkOrder;
import uo.ri.cws.domain.WorkOrder.WorkOrderStatus;

public class CreateInvoiceFor implements Command<InvoiceDto>{

	private List<String> workOrderIds;
	private WorkOrderRepository wor = Factory.repository.forWorkOrder();
	private InvoiceRepository ir = Factory.repository.forInvoice();

	public CreateInvoiceFor(List<String> workOrderIds) {
		ArgumentChecks.isNotNull( workOrderIds );
		this.workOrderIds = workOrderIds;
	}

	@Override
	public InvoiceDto execute() throws BusinessException {
		Long number = ir.getNextInvoiceNumber();
		List<WorkOrder> workOrders = wor.findByIds(workOrderIds);
		
		// Security check
		checkWorkOrders(workOrders);				
		
		Invoice in = new Invoice(number, workOrders);
		ir.add(in);
		return DtoAssembler.toDto(in);
	}
	
	/**
	 * Method which checks that everything is alright with the workOrders
	 * @param workOrders
	 * @throws BusinessException
	 */
	private void checkWorkOrders(List<WorkOrder> workOrders) throws BusinessException {
		BusinessChecks.isFalse(workOrders.isEmpty(), "No workOrders");
		BusinessChecks.isNotNull(workOrders);
		
		for(WorkOrder w : workOrders) {
			BusinessChecks.isTrue(w.getStatus().equals(WorkOrderStatus.FINISHED), "One of the WorkOrders is not terminated");
		}
	}
}
