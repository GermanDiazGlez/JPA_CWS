package uo.ri.ui.manager.spares.sparepart.action;

import java.util.Optional;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.conf.Factory;
import uo.ri.cws.application.service.spare.SparePartReportService;
import uo.ri.cws.application.service.spare.SparePartReportService.SparePartReportDto;
import uo.ri.ui.util.Printer;

public class ViewDetailAction implements Action {

	@Override
	public void execute() throws Exception {
		String code = Console.readString("Code: ");
		
		SparePartReportService service = Factory.service.forSparePartReportService();
		Optional<SparePartReportDto> op = service.findByCode( code );
		
		if ( op.isEmpty() ) {
			Console.println("There is no such spare part.");
			Console.println("Please mind the code and try again.");
			return;
		}
		
		Printer.print( op.get() );
	}

}
