package uo.ri.cws.application.service.spare.provider.crud;

import java.util.List;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ProviderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.spare.ProvidersCrudService.ProviderDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Provider;

public class FindProvidersBySparePartCode
		implements Command<List<ProviderDto>> {

	private ProviderRepository repo = Factory.repository.forProvider();
	private String code;

	public FindProvidersBySparePartCode(String code) {
		ArgumentChecks.isNotNull( code );
		this.code = code;
	}

	@Override
	public List<ProviderDto> execute() throws BusinessException {
		List<Provider> list = repo.findBySparePartCode( code );
		return DtoAssembler.toProvidersDtoList( list );
	}

}
