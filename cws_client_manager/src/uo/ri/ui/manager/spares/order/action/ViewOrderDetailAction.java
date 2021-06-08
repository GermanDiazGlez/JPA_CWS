package uo.ri.ui.manager.spares.order.action;

import java.util.Optional;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.spare.OrdersService;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto;
import uo.ri.ui.util.Printer;

public class ViewOrderDetailAction implements Action {

	@Override
	public void execute() throws Exception {
		String orderCode = Console.readString("Order code: ");
		
		OrdersService service = Factory.service.forOrdersService();
		Optional<OrderDto> oo = service.findByCode( orderCode );
		
		if ( oo.isEmpty()) {
			Console.println("There is no order with such code");
			return;
		}
			
		Printer.printDetail( oo.get() );
	}

}
