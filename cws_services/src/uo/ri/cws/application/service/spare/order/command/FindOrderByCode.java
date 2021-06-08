package uo.ri.cws.application.service.spare.order.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.OrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;

public class FindOrderByCode implements Command<Optional<OrderDto>> {

	private String code;
	private OrderRepository repo = Factory.repository.forOrder();

	public FindOrderByCode(String code) {
		this.code = code;
	}

	@Override
	public Optional<OrderDto> execute() throws BusinessException {
		return repo.findByCode( code ).map( o -> DtoAssembler.toDto( o ) );
	}

}
