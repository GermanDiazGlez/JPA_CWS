package uo.ri.cws.application.service.spare.supply;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.spare.SuppliesCrudService;
import uo.ri.cws.application.service.spare.supply.crud.AddSupply;
import uo.ri.cws.application.service.spare.supply.crud.DeleteSupply;
import uo.ri.cws.application.service.spare.supply.crud.FindSuppliesByProviderNif;
import uo.ri.cws.application.service.spare.supply.crud.FindSuppliesBySparePartCode;
import uo.ri.cws.application.service.spare.supply.crud.FindSupplyByNifAndCode;
import uo.ri.cws.application.service.spare.supply.crud.UpdateSupply;
import uo.ri.cws.application.util.command.CommandExecutor;

public class SuppliesCrudServiceImpl implements SuppliesCrudService {

	private CommandExecutor executor = Factory.executor.forExecutor();

	@Override
	public String add(SupplyDto dto) throws BusinessException {
		return executor.execute( new AddSupply(dto) );
	}

	@Override
	public void delete(String nif, String code) throws BusinessException {
		executor.execute( new DeleteSupply(nif, code) );
	}

	@Override
	public void update(SupplyDto dto) throws BusinessException {
		executor.execute( new UpdateSupply(dto) );
	}

	@Override
	public Optional<SupplyDto> findByNifAndCode(String nif, String code) throws BusinessException {
		return executor.execute( new FindSupplyByNifAndCode(nif, code) );
	}

	@Override
	public List<SupplyDto> findByProviderNif(String nif) throws BusinessException {
		return executor.execute( new FindSuppliesByProviderNif(nif) );
	}

	@Override
	public List<SupplyDto> findBySparePartCode(String code) throws BusinessException {
		return executor.execute( new FindSuppliesBySparePartCode(code) );
	}

}
