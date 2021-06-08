package uo.ri.ui.manager.spares.order.action;

import java.util.Optional;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.spare.OrdersService;
import uo.ri.cws.application.service.spare.OrdersService.OrderDto;
import uo.ri.ui.util.Printer;

public class ReceiveOrderAction implements Action {

	@Override
	public void execute() throws Exception {
		String code = Console.readString("Please, type the order code: ");
		
		OrdersService service = Factory.service.forOrdersService();
		Optional<OrderDto> order = service.findByCode( code );

		if ( order.isEmpty()) {
			Console.println("There is no order with such code");
			return;
		}

		Console.println("Please, review the content of the order received");
		Printer.printDetail( order.get() );
		
		String yesNo = Console.readString("Is all right? (y/n)");
		if ( yesNo.equalsIgnoreCase( "y" ) ) {
			service.receive(code);
			Console.println("The reception has been registered and the prices updated");
		} else {
			Console.println("Please, contact the provider then try again.");
			Console.println("The operation has been aborted.");
		}

	}

}
