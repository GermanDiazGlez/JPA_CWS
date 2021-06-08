package uo.ri.cws.application.service.spare.provider;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.spare.ProvidersCrudService;
import uo.ri.cws.application.service.spare.provider.crud.AddProvider;
import uo.ri.cws.application.service.spare.provider.crud.DeleteProvider;
import uo.ri.cws.application.service.spare.provider.crud.FindProviderByNif;
import uo.ri.cws.application.service.spare.provider.crud.FindProvidersByName;
import uo.ri.cws.application.service.spare.provider.crud.FindProvidersBySparePartCode;
import uo.ri.cws.application.service.spare.provider.crud.UpdateProvider;
import uo.ri.cws.application.util.command.CommandExecutor;

public class ProvidersCrudServiceImpl implements ProvidersCrudService {

	private CommandExecutor executor = Factory.executor.forExecutor();

	@Override
	public String add(ProviderDto dto) throws BusinessException {
		return executor.execute( new AddProvider(dto) );
	}

	@Override
	public void delete(String nif) throws BusinessException {
		executor.execute( new DeleteProvider( nif ) );
	}

	@Override
	public void update(ProviderDto dto) throws BusinessException {
		executor.execute( new UpdateProvider( dto ) );
	}

	@Override
	public Optional<ProviderDto> findByNif(String nif) throws BusinessException {
		return executor.execute( new FindProviderByNif(nif) );
	}

	@Override
	public List<ProviderDto> findByName(String name) throws BusinessException {
		return executor.execute( new FindProvidersByName(name) );
	}

	@Override
	public List<ProviderDto> findBySparePartCode(String code) throws BusinessException {
		return executor.execute( new FindProvidersBySparePartCode(code) );
	}

}
