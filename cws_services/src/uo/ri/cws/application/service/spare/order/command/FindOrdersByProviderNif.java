package uo.ri.cws.application.service.spare.order.command;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.OrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Order;

public class FindOrdersByProviderNif implements Command<List<OrderDto>> {

	private OrderRepository repo = Factory.repository.forOrder();
	private String nif;

	public FindOrdersByProviderNif(String nif) {
		this.nif = nif;
	}

	@Override
	public List<OrderDto> execute() throws BusinessException {
		List<Order> orders = repo.findByProviderNif( nif );
		return DtoAssembler.toOrdersDtoList( orders );
	}

}
