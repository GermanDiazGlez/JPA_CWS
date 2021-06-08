package uo.ri.cws.application.service.spare.sparepart.report;

import java.util.List;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.SparePartRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.spare.SparePartReportService.SparePartReportDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.SparePart;

public class FindSparePartsReportByDescription implements Command<List<SparePartReportDto>> {

	private SparePartRepository repo = Factory.repository.forSparePart();
	private String description;

	public FindSparePartsReportByDescription(String description) {
		ArgumentChecks.isNotNull( description );
		this.description = description;
	}

	@Override
	public List<SparePartReportDto> execute() throws BusinessException {
		List<SparePart> list = repo.findByDescription( description );
		return DtoAssembler.toSparePartRepoDtoList( list );
	}

}
