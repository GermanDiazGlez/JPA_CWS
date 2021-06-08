package uo.ri.ui.manager.spares.supply.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.spare.SuppliesCrudService;

public class DeleteAction implements Action {

	@Override
	public void execute() throws Exception {
		Console.println("Please, provide the following data: ");
		String nif = Console.readString("Provider nif: ");
		String code = Console.readString("Spare part code: ");

		SuppliesCrudService service = Factory.service.forSuppliesCrudService();
		service.delete(nif, code);

		Console.println("The supply has been deleted");
	}

}
