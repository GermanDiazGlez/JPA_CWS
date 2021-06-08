package uo.ri.cws.application.service.spare.supply.crud;

import java.util.List;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.SupplyRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.spare.SuppliesCrudService.SupplyDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Supply;

public class FindSuppliesBySparePartCode implements Command<List<SupplyDto>> {

	private SupplyRepository repo = Factory.repository.forSupply();
	private String code;

	public FindSuppliesBySparePartCode(String code) {
		ArgumentChecks.isNotNull( code );
		this.code = code;
	}

	@Override
	public List<SupplyDto> execute() throws BusinessException {
		List<Supply> list = repo.findBySparePartCode( code );
		return DtoAssembler.toSupplyDtoList( list );
	}

}
