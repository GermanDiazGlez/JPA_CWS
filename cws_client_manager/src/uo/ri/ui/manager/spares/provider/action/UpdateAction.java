package uo.ri.ui.manager.spares.provider.action;

import java.util.Optional;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.spare.ProvidersCrudService;
import uo.ri.cws.application.service.spare.ProvidersCrudService.ProviderDto;
import uo.ri.ui.util.Printer;

public class UpdateAction implements Action {

	@Override
	public void execute() throws Exception {
		Console.println("Please, provide the following data: ");
		String nif = Console.readString("Nif: ");
		
		ProvidersCrudService service = Factory.service.forProvidersService();
		Optional<ProviderDto> op = service.findByNif( nif );
		
		if ( op.isEmpty() ) {
			Console.println("There is no such provider.");
			Console.println("Please mind the nif and try again.");
			return;
		}
		
		ProviderDto dto = op.get();
		Console.println("Current data for the provider: ");
		Printer.print( dto );
		
		dto.name = Console.readString("new name: ", dto.name);
		dto.email = Console.readString("new email: ", dto.email);
		dto.phone = Console.readString("new phone: ", dto.phone);
		
		service.update(dto); // dto keeps the id and version
		
		Console.println("The provider has been updated");
	}

}
