package uo.ri.cws.application.service.spare.order.command;

import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.OrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Order;

public class ReceiveOrder implements Command<OrderDto> {

	private OrderRepository repo = Factory.repository.forOrder();
	private String code;

	public ReceiveOrder(String code) {
		ArgumentChecks.isNotEmpty( code );
		this.code = code;
	}

	@Override
	public OrderDto execute() throws BusinessException {
		Optional<Order> oo = repo.findByCode(code);
		BusinessChecks.exists( oo , "There is no such order");
		
		Order o = oo.get();
		checkIsPending( o );
		
		o.receive();
		
		return DtoAssembler.toDto(o);
	}

	private void checkIsPending(Order o) throws BusinessException {
		BusinessChecks.isTrue( o.isPending(), "The order is not PENDING");
	}

}
