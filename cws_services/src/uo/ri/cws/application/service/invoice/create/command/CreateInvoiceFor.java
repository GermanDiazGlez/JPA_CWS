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
	private WorkOrderRepository wrkrsRepo = Factory.repository.forWorkOrder();
	private InvoiceRepository invsRepo = Factory.repository.forInvoice();

	public CreateInvoiceFor(List<String> workOrderIds) {
		ArgumentChecks.isNotNull( workOrderIds );
		this.workOrderIds = workOrderIds;
	}

	@Override
	public InvoiceDto execute() throws BusinessException {
		Long number = invsRepo.getNextInvoiceNumber();
		List<WorkOrder> workOrders = wrkrsRepo.findByIds(workOrderIds);
		
		// Security check
		checkWorkOrders(workOrders);				
		
		Invoice in = new Invoice(number, workOrders);
		invsRepo.add(in);
		return DtoAssembler.toDto(in);
	}
	
	/**
	 * Method which checks that everything is alright with the workOrders
	 * @param workOrders
	 * @throws BusinessException
	 */
	private void checkWorkOrders(List<WorkOrder> workOrders) throws BusinessException {
		BusinessChecks.isFalse(workOrders.isEmpty(), "No workOrders");
		
		for(WorkOrder w : workOrders) {
			BusinessChecks.isFalse(w.getStatus().equals(WorkOrderStatus.INVOICED), "WorkOrder has been invoiced");
			BusinessChecks.isTrue(w.getStatus().equals(WorkOrderStatus.FINISHED), "WorkOrder is not terminated");
		}
	}
}
