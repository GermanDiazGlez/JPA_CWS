package uo.ri.cws.application.service.spare.order;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.spare.OrdersService;
import uo.ri.cws.application.service.spare.order.command.FindOrderByCode;
import uo.ri.cws.application.service.spare.order.command.FindOrdersByProviderNif;
import uo.ri.cws.application.service.spare.order.command.GenerateOrders;
import uo.ri.cws.application.service.spare.order.command.ReceiveOrder;
import uo.ri.cws.application.util.command.CommandExecutor;

public class OrdersServiceImpl implements OrdersService {

	private CommandExecutor executor = Factory.executor.forExecutor();

	@Override
	public List<OrderDto> generateOrders() throws BusinessException {
		return executor.execute( new GenerateOrders() );
	}

	@Override
	public List<OrderDto> findByProviderNif(String nif) throws BusinessException {
		return executor.execute( new FindOrdersByProviderNif(nif) );
	}

	@Override
	public Optional<OrderDto> findByCode(String code) throws BusinessException {
		return executor.execute( new FindOrderByCode(code) );
	}

	@Override
	public OrderDto receive(String code) throws BusinessException {
		return executor.execute( new ReceiveOrder(code) );
	}

}
