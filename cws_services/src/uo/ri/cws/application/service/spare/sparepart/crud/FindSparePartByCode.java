package uo.ri.cws.application.service.spare.sparepart.crud;

import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.SparePartRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.spare.SparePartCrudService.SparePartDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;

public class FindSparePartByCode implements Command<Optional<SparePartDto>> {

	private SparePartRepository repo = Factory.repository.forSparePart();
	private String code;

	public FindSparePartByCode(String code) {
		ArgumentChecks.isNotNull( code );
		this.code = code;
	}

	@Override
	public Optional<SparePartDto> execute() throws BusinessException {
		return repo.findByCode(code).map(o -> DtoAssembler.toDto(o));
	}

}
