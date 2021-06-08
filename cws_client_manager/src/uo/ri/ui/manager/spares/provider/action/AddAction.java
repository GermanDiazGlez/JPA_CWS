package uo.ri.ui.manager.spares.provider.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.spare.ProvidersCrudService;
import uo.ri.cws.application.service.spare.ProvidersCrudService.ProviderDto;

public class AddAction implements Action {

	@Override
	public void execute() throws Exception {
		ProviderDto dto = new ProviderDto();
		
		Console.println("Please, provide the following data: ");
		dto.nif = Console.readString("Nif: ");
		dto.name = Console.readString("Name: ");
		dto.email = Console.readString("Email: ");
		dto.phone = Console.readString("Phone: ");
		
		ProvidersCrudService service = Factory.service.forProvidersService();
		String id = service.add(dto);
		
		Console.println("The new provider has been registered with id " +  id);
	}

}
