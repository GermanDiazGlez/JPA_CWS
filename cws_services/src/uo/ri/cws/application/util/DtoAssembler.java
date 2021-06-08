package uo.ri.cws.application.util;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import uo.ri.cws.application.service.client.ClientCrudService.ClientDto;
import uo.ri.cws.application.service.invoice.InvoicingService.CardDto;
import uo.ri.cws.application.service.invoice.InvoicingService.CashDto;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.cws.application.service.invoice.InvoicingService.PaymentMeanDto;
import uo.ri.cws.application.service.invoice.InvoicingService.VoucherDto;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto.OrderLineDto;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto.OrderedProviderDto;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto.OrderedSpareDto;
import uo.ri.cws.application.service.spare.ProvidersCrudService.ProviderDto;
import uo.ri.cws.application.service.spare.SparePartCrudService.SparePartDto;
import uo.ri.cws.application.service.spare.SparePartReportService.SparePartReportDto;
import uo.ri.cws.application.service.spare.SuppliesCrudService.SupplyDto;
import uo.ri.cws.application.service.vehicle.VehicleCrudService.VehicleDto;
import uo.ri.cws.application.service.vehicletype.VehicleTypeCrudService.VehicleTypeDto;
import uo.ri.cws.application.service.workorder.WorkOrderCrudService.WorkOrderDto;
import uo.ri.cws.domain.Cash;
import uo.ri.cws.domain.Client;
import uo.ri.cws.domain.CreditCard;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.Order;
import uo.ri.cws.domain.OrderLine;
import uo.ri.cws.domain.PaymentMean;
import uo.ri.cws.domain.Provider;
import uo.ri.cws.domain.SparePart;
import uo.ri.cws.domain.Supply;
import uo.ri.cws.domain.Vehicle;
import uo.ri.cws.domain.VehicleType;
import uo.ri.cws.domain.Voucher;
import uo.ri.cws.domain.WorkOrder;

public class DtoAssembler {

	public static ClientDto toDto(Client c) {
		 ClientDto dto = new ClientDto();

		 dto.id = c.getId();
		 dto.version = c.getVersion();

		 dto.dni = c.getDni();
		 dto.name = c.getName();
		 dto.surname = c.getSurname();

		 return dto;
	}

	public static List<ClientDto> toClientDtoList(List<Client> clientes) {
		List<ClientDto> res = new ArrayList<>();
		for(Client c: clientes) {
			res.add( DtoAssembler.toDto( c ) );
		}
		return res;
	}

	public static MechanicDto toDto(Mechanic m) {
		MechanicDto dto = new MechanicDto();
		dto.id = m.getId();
		dto.version = m.getVersion();

		dto.dni = m.getDni();
		dto.name = m.getName();
		dto.surname = m.getSurname();
		return dto;
	}

	public static List<MechanicDto> toMechanicDtoList(List<Mechanic> list) {
		List<MechanicDto> res = new ArrayList<>();
		for(Mechanic m: list) {
			res.add( toDto( m ) );
		}
		return res;
	}

	public static List<VoucherDto> toVoucherDtoList(List<Voucher> list) {
		List<VoucherDto> res = new ArrayList<>();
		for(Voucher b: list) {
			res.add( toDto( b ) );
		}
		return res;
	}

	public static VoucherDto toDto(Voucher v) {
		VoucherDto dto = new VoucherDto();
		dto.id = v.getId();
		dto.version = v.getVersion();

		dto.clientId = v.getClient().getId();
		dto.accumulated = v.getAccumulated();
		dto.code = v.getCode();
		dto.description = v.getDescription();
		dto.available = v.getAvailable();
		return dto;
	}

	public static CardDto toDto(CreditCard cc) {
		CardDto dto = new CardDto();
		dto.id = cc.getId();
		dto.version = cc.getVersion();

		dto.clientId = cc.getClient().getId();
		dto.accumulated = cc.getAccumulated();
		dto.cardNumber = cc.getNumber();
		dto.cardExpiration = cc.getValidThru();
		dto.cardType = cc.getType();
		return dto;
	}

	public static CashDto toDto(Cash m) {
		CashDto dto = new CashDto();
		dto.id = m.getId();
		dto.version = m.getVersion();

		dto.clientId = m.getClient().getId();
		dto.accumulated = m.getAccumulated();
		return dto;
	}

	public static InvoiceDto toDto(Invoice invoice) {
		InvoiceDto dto = new InvoiceDto();
		dto.id = invoice.getId();
		dto.version = invoice.getVersion();

		dto.number = invoice.getNumber();
		dto.date = invoice.getDate();
		dto.total = invoice.getAmount();
		dto.vat = invoice.getVat();
		dto.status = invoice.getStatus().toString();
		return dto;
	}

	public static List<PaymentMeanDto> toPaymentMeanDtoList(List<PaymentMean> list) {
		return list.stream()
				.map( mp -> toDto( mp ) )
				.collect( Collectors.toList() );
	}

	private static PaymentMeanDto toDto(PaymentMean mp) {
		if (mp instanceof Voucher) {
			return toDto( (Voucher) mp );
		}
		else if (mp instanceof CreditCard) {
			return toDto( (CreditCard) mp );
		}
		else if (mp instanceof Cash) {
			return toDto( (Cash) mp);
		}
		else {
			throw new RuntimeException("Unexpected type of payment mean");
		}
	}

	public static WorkOrderDto toDto(WorkOrder a) {
		WorkOrderDto dto = new WorkOrderDto();
		dto.id = a.getId();
		dto.version = a.getVersion();

		dto.vehicleId = a.getVehicle().getId();
		dto.description = a.getDescription();
		dto.date = a.getDate();
		dto.total = a.getAmount();
		dto.status = a.getStatus().toString();

		dto.invoiceId = a.getInvoice() == null ? null : a.getInvoice().getId();

		return dto;
	}

	public static VehicleDto toDto(Vehicle v) {
		VehicleDto dto = new VehicleDto();
		dto.id = v.getId();
		dto.version = v.getVersion();

		dto.plate = v.getPlateNumber();
		dto.clientId = v.getClient().getId();
		dto.make = v.getMake();
		dto.vehicleTypeId = v.getVehicleType().getId();
		dto.model = v.getModel();

		return dto;
	}

	public static List<WorkOrderDto> toWorkOrderDtoList(List<WorkOrder> list) {
		return list.stream()
				.map( a -> toDto( a ) )
				.collect( Collectors.toList() );
	}

	public static VehicleTypeDto toDto(VehicleType vt) {
		VehicleTypeDto dto = new VehicleTypeDto();

		dto.id = vt.getId();
		dto.version = vt.getVersion();

		dto.name = vt.getName();
		dto.pricePerHour = vt.getPricePerHour();

		return dto;
	}

	public static List<VehicleTypeDto> toVehicleTypeDtoList(
			List<VehicleType> list) {
		return list.stream()
				.map( a -> toDto( a ) )
				.collect( Collectors.toList() );
	}

	public static OrderDto toDto(Order o) {
		OrderDto dto = new OrderDto();

		dto.id = o.getId();
		dto.version = o.getVersion();

		dto.code = o.getCode();
		dto.orderedDate = o.getOrderedDate();
		dto.receptionDate = o.getReceptionDate();
		dto.status = o.getStatus().toString();
		dto.amount = o.getAmount();

		for(OrderLine line: o.getOrderLines() ) {
			dto.lines.add( toDto( line ) );
		}

		dto.provider = toOrderedProviderDto( o.getProvider() );

		return dto;
	}

	public static OrderLineDto toDto(OrderLine line) {
		OrderLineDto dto = new OrderLineDto();

		dto.quantity = line.getQuantity();
		dto.price = line.getPrice();
		dto.sparePart = toOrderedSpareDto( line.getSparePart() );

		return dto;
	}

	public static OrderedSpareDto toOrderedSpareDto(SparePart s) {
		OrderedSpareDto dto = new OrderedSpareDto();

		dto.id = s.getId();
		dto.code = s.getCode();
		dto.description = s.getDescription();

		return dto;
	}

	public static OrderedProviderDto toOrderedProviderDto(Provider p) {
		OrderedProviderDto dto = new OrderedProviderDto();

		dto.id = p.getId();
		dto.name = p.getName();
		dto.nif = p.getNif();

		return dto;
	}

	public static List<OrderDto> toOrdersDtoList(List<Order> list) {
		return list.stream()
				.map( a -> toDto( a ) )
				.collect( Collectors.toList() );
	}

	public static ProviderDto toDto(Provider p) {
		ProviderDto dto = new ProviderDto();
		dto.id = p.getId();
		dto.version = p.getVersion();

		dto.nif = p.getNif();
		dto.name = p.getName();
		dto.email = p.getEmail();
		dto.phone = p.getPhone();

		return dto;
	}

	public static List<ProviderDto> toProvidersDtoList(List<Provider> list) {
		return list.stream()
				.map( a -> toDto( a ) )
				.collect( Collectors.toList() );
	}

	public static SparePartDto toDto(SparePart sp) {
		SparePartDto dto = new SparePartDto();
		dto.id = sp.getId();
		dto.version = sp.getVersion();

		dto.code = sp.getCode();
		dto.description = sp.getDescription();
		dto.price = sp.getPrice();
		dto.stock = sp.getStock();
		dto.minStock = sp.getMinStock();
		dto.maxStock = sp.getMaxStock();

		return dto;
	}

	public static SparePartReportDto toSpareReportDto(SparePart sp) {
		SparePartReportDto dto = new SparePartReportDto();
		dto.id = sp.getId();
		dto.version = sp.getVersion();

		dto.code = sp.getCode();
		dto.description = sp.getDescription();
		dto.price = sp.getPrice();
		dto.stock = sp.getStock();
		dto.minStock = sp.getMinStock();
		dto.maxStock = sp.getMaxStock();

		// this will be better with a query
		dto.totalUnitsSold = sp.getTotalUnitsSold();

		return dto;
	}

	public static List<SparePartReportDto> toSparePartRepoDtoList(
			List<SparePart> list) {
		return list.stream()
				.map( a -> toSpareReportDto( a ) )
				.collect( Collectors.toList() );
	}

	public static SupplyDto toDto(Supply s) {
		SupplyDto dto = new SupplyDto();
		dto.id = s.getId();
		dto.version = s.getVersion();

		dto.provider.id = s.getProvider().getId();
		dto.provider.nif = s.getProvider().getNif();
		dto.provider.name = s.getProvider().getName();

		dto.sparePart.id = s.getSparePart().getId();
		dto.sparePart.code = s.getSparePart().getCode();
		dto.sparePart.description = s.getSparePart().getDescription();

		dto.price = s.getPrice();
		dto.deliveryTerm = s.getDeliveryTerm();
		return dto;
	}

	public static List<SupplyDto> toSupplyDtoList(List<Supply> list) {
		return list.stream()
				.map( a -> toDto( a ) )
				.collect( Collectors.toList() );
	}

	public static List<InvoicingWorkOrderDto> toInvoicingWorkOrderDtoList(
			List<WorkOrder> list) {
		return list.stream()
				.map( a -> toInvoicingWorkOrderDto( a ) )
				.collect( Collectors.toList() );
	}

	private static InvoicingWorkOrderDto toInvoicingWorkOrderDto(WorkOrder w) {
		InvoicingWorkOrderDto dto = new InvoicingWorkOrderDto();

		dto.id = w.getId();
		dto.description = w.getDescription();
		dto.date = w.getDate();
		dto.status = w.getStatus().toString();
		dto.total = w.getAmount();

		return dto;
	}

}
