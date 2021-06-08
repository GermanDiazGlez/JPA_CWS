package uo.ri.cws.application.repository;

public interface RepositoryFactory {

	MechanicRepository forMechanic();
	WorkOrderRepository forWorkOrder();
	PaymentMeanRepository forPaymentMean();
	InvoiceRepository forInvoice();
	ClientRepository forClient();
	SparePartRepository forSparePart();
	InterventionRepository forIntervention();
	VehicleRepository forVehicle();
	VehicleTypeRepository forVehicleType();
	OrderRepository forOrder();
	SupplyRepository forSupply();
	ProviderRepository forProvider();
	ChargeRepository forCharge();

}
