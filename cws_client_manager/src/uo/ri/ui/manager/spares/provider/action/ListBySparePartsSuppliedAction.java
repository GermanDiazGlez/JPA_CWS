package uo.ri.ui.manager.spares.provider.action;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.spare.ProvidersCrudService;
import uo.ri.cws.application.service.spare.ProvidersCrudService.ProviderDto;
import uo.ri.ui.util.Printer;

public class ListBySparePartsSuppliedAction implements Action {

	@Override
	public void execute() throws Exception {
		String code = Console.readString("Spare part code: ");
		
		ProvidersCrudService service = Factory.service.forProvidersService();
		List<ProviderDto> providers = service.findBySparePartCode(code);
		
		Console.println("There are " + providers.size() + " providers");
		for(ProviderDto p: providers) {
			Printer.print(p);
		}
	}

}
