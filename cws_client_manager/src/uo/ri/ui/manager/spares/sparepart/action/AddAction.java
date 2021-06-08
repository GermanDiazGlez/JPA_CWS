package uo.ri.ui.manager.spares.sparepart.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.spare.SparePartCrudService;
import uo.ri.cws.application.service.spare.SparePartCrudService.SparePartDto;

public class AddAction implements Action {

	@Override
	public void execute() throws Exception {
		SparePartDto dto = new SparePartDto();
		
		Console.println("Please, provide the following data: ");
		dto.code = Console.readString("Code: ");
		dto.description = Console.readString("Description: ");
		dto.stock = Console.readInt("Current stock: ");
		dto.minStock = Console.readInt("Minimum stock: ");
		dto.maxStock = Console.readInt("Maximum stock: ");
		dto.price = Console.readDouble("Price: ");
		
		SparePartCrudService service = Factory.service.forSparePartCrudService();
		String id = service.add(dto);
		
		Console.println("The new spare part has been registered with id " +  id);
	}

}
