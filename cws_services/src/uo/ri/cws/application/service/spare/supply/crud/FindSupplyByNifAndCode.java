package uo.ri.cws.application.service.spare.supply.crud;

import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.SupplyRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.spare.SuppliesCrudService.SupplyDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;

public class FindSupplyByNifAndCode implements Command<Optional<SupplyDto>> {

	private SupplyRepository repo = Factory.repository.forSupply();
	private String nif;
	private String code;

	public FindSupplyByNifAndCode(String nif, String code) {
		ArgumentChecks.isNotEmpty(nif);
		ArgumentChecks.isNotEmpty(code);
		this.nif = nif;
		this.code = code;
	}

	@Override
	public Optional<SupplyDto> execute() throws BusinessException {
		return repo.findByNifAndCode(nif, code)
				.map(o -> DtoAssembler.toDto(o));
	}

}
