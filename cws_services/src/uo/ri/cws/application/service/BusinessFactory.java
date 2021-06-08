package uo.ri.cws.application.service;

import uo.ri.cws.application.ServiceFactory;
import uo.ri.cws.application.service.client.ClientCrudService;
import uo.ri.cws.application.service.client.ClientHistoryService;
import uo.ri.cws.application.service.invoice.InvoicingService;
import uo.ri.cws.application.service.invoice.create.InvoicingServiceImpl;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.crud.MechanicCrudServiceImpl;
import uo.ri.cws.application.service.spare.OrdersService;
import uo.ri.cws.application.service.spare.ProvidersCrudService;
import uo.ri.cws.application.service.spare.SparePartCrudService;
import uo.ri.cws.application.service.spare.SparePartReportService;
import uo.ri.cws.application.service.spare.SuppliesCrudService;
import uo.ri.cws.application.service.spare.order.OrdersServiceImpl;
import uo.ri.cws.application.service.spare.provider.ProvidersCrudServiceImpl;
import uo.ri.cws.application.service.spare.sparepart.SparePartCrudServiceImpl;
import uo.ri.cws.application.service.spare.sparepart.SparePartReportServiceImpl;
import uo.ri.cws.application.service.spare.supply.SuppliesCrudServiceImpl;
import uo.ri.cws.application.service.vehicle.VehicleCrudService;
import uo.ri.cws.application.service.vehicle.crud.VehicleCrudServiceImpl;
import uo.ri.cws.application.service.vehicletype.VehicleTypeCrudService;
import uo.ri.cws.application.service.workorder.CloseWorkOrderService;
import uo.ri.cws.application.service.workorder.ViewAssignedWorkOrdersService;
import uo.ri.cws.application.service.workorder.WorkOrderCrudService;

public class BusinessFactory implements ServiceFactory {

	@Override
	public MechanicCrudService forMechanicCrudService() {
		return new MechanicCrudServiceImpl();
	}

	@Override
	public InvoicingService forCreateInvoiceService() {
		return new InvoicingServiceImpl();
	}

	@Override
	public VehicleCrudService forVehicleCrudService() {
		return new VehicleCrudServiceImpl();
	}

	@Override
	public CloseWorkOrderService forClosingBreakdown() {
		throw new RuntimeException("Not yet implemented");
	}

	@Override
	public VehicleTypeCrudService forVehicleTypeCrudService() {
		throw new RuntimeException("Not yet implemented");
	}

	@Override
	public SparePartCrudService forSparePartCrudService() {
		return new SparePartCrudServiceImpl();
	}

	@Override
	public ClientCrudService forClienteCrudService() {
		throw new RuntimeException("Not yet implemented");
	}

	@Override
	public ClientHistoryService forClientHistoryService() {
		throw new RuntimeException("Not yet implemented");
	}

	@Override
	public WorkOrderCrudService forWorkOrderCrudService() {
		throw new RuntimeException("Not yet implemented");
	}

	@Override
	public ViewAssignedWorkOrdersService forViewAssignedWorkOrdersService() {
		throw new RuntimeException("Not yet implemented");
	}

	@Override
	public ProvidersCrudService forProvidersService() {
		return new ProvidersCrudServiceImpl();
	}

	@Override
	public OrdersService forOrdersService() {
		return new OrdersServiceImpl();
	}

	@Override
	public SuppliesCrudService forSuppliesCrudService() {
		return new SuppliesCrudServiceImpl();
	}

	@Override
	public SparePartReportService forSparePartReportService() {
		return new SparePartReportServiceImpl();
	}

}
