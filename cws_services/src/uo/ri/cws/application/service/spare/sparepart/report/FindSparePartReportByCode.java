package uo.ri.cws.application.service.spare.sparepart.report;

import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.SparePartRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.spare.SparePartReportService.SparePartReportDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;

public class FindSparePartReportByCode implements Command<Optional<SparePartReportDto>> {

	private SparePartRepository repo = Factory.repository.forSparePart();
	private String code;

	public FindSparePartReportByCode(String code) {
		ArgumentChecks.isNotNull( code );
		this.code = code;
	}

	@Override
	public Optional<SparePartReportDto> execute() throws BusinessException {
		return repo.findByCode(code).map(o -> DtoAssembler.toSpareReportDto(o));
	}

}
