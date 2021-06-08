package uo.ri.cws.application.service.spare.provider.crud;

import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ProviderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.spare.ProvidersCrudService.ProviderDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;

public class FindProviderByNif implements Command<Optional<ProviderDto>> {

	private ProviderRepository repo = Factory.repository.forProvider();
	private String nif;

	public FindProviderByNif(String nif) {
		ArgumentChecks.isNotNull(nif);
		this.nif = nif;
	}

	@Override
	public Optional<ProviderDto> execute() throws BusinessException {
		return repo.findByNif(nif).map(o -> DtoAssembler.toDto(o));
	}

}
