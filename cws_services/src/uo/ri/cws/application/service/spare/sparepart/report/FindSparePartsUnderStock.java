package uo.ri.cws.application.service.spare.sparepart.report;

import java.util.List;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.SparePartRepository;
import uo.ri.cws.application.service.spare.SparePartReportService.SparePartReportDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.SparePart;

public class FindSparePartsUnderStock implements Command<List<SparePartReportDto>> {

	private SparePartRepository repo = Factory.repository.forSparePart();

	@Override
	public List<SparePartReportDto> execute() {
		List<SparePart> list = repo.findUnderStock();
		return DtoAssembler.toSparePartRepoDtoList( list );
	}

}
