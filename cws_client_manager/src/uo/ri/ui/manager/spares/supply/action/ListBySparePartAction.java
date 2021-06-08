package uo.ri.ui.manager.spares.supply.action;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.spare.SuppliesCrudService;
import uo.ri.cws.application.service.spare.SuppliesCrudService.SupplyDto;
import uo.ri.ui.util.Printer;

public class ListBySparePartAction implements Action {

	@Override
	public void execute() throws Exception {
		String code = Console.readString("Spare part code: ");
		
		SuppliesCrudService service = Factory.service.forSuppliesCrudService();
		List<SupplyDto> supplies = service.findBySparePartCode( code );
		
		Console.println("There are " + supplies.size() + " supplies for the spare part");
		for(SupplyDto p: supplies) {
			Printer.print(p);
		}
	}

}
