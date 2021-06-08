package uo.ri.ui.manager.spares.sparepart.action;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.spare.SparePartReportService;
import uo.ri.cws.application.service.spare.SparePartReportService.SparePartReportDto;
import uo.ri.ui.util.Printer;

public class ListUnderStockAction implements Action {

	@Override
	public void execute() throws Exception {
		SparePartReportService service = Factory.service.forSparePartReportService();
		List<SparePartReportDto> spares = service.findUnderStock();
		
		Console.println("There are " + spares.size() + " spares under stock");
		for(SparePartReportDto sp: spares) {
			Printer.print(sp);
		}
	}

}
