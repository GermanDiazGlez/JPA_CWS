package uo.ri.ui.manager.spares.provider.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.spare.ProvidersCrudService;

public class DeleteAction implements Action {

	@Override
	public void execute() throws Exception {
		Console.println("Please, provide the following data: ");
		String nif = Console.readString("Nif: ");
		
		ProvidersCrudService service = Factory.service.forProvidersService();
		service.delete(nif);

		Console.println("The provider has been deleted");
	}

}
