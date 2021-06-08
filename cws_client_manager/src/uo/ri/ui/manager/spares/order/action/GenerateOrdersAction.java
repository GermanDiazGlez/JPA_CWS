package uo.ri.ui.manager.spares.order.action;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.spare.OrdersService;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto;
import uo.ri.ui.util.Printer;

public class GenerateOrdersAction implements Action {

	@Override
	public void execute() throws Exception {
		Console.println("New orders are about to be generated");
		
		OrdersService service = Factory.service.forOrdersService();
		
		List<OrderDto> orders = service.generateOrders();
		Console.println( orders.size() + " have been generated.");
		for(OrderDto order: orders) {
			Printer.printSummary(order);
		}

	}

}
