package uo.ri.ui.manager.spares.sparepart.action;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.spare.SparePartReportService;
import uo.ri.cws.application.service.spare.SparePartReportService.SparePartReportDto;
import uo.ri.ui.util.Printer;

public class ListByDescriptionAction implements Action {

	@Override
	public void execute() throws Exception {
		String desc = Console.readString("Spare part description (may be partial): ");
		
		SparePartReportService service = Factory.service.forSparePartReportService();
		List<SparePartReportDto> spares = service.findByDescription( desc );
		
		Console.println("There are " + spares.size() + " spare parts");
		for(SparePartReportDto p: spares) {
			Printer.print(p);
		}
	}

}
