package uo.ri.ui.manager.spares.sparepart.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.spare.SparePartCrudService;

public class DeleteAction implements Action {

	@Override
	public void execute() throws Exception {
		String code = Console.readString("Code: ");
		
		SparePartCrudService service = Factory.service.forSparePartCrudService();
		service.delete(code);

		Console.println("The spare part has been deleted");
	}

}
