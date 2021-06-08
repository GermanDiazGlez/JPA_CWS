package uo.ri.cws.application.service.spare.sparepart;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.spare.SparePartCrudService;
import uo.ri.cws.application.service.spare.sparepart.crud.AddSparePart;
import uo.ri.cws.application.service.spare.sparepart.crud.DeleteSparePart;
import uo.ri.cws.application.service.spare.sparepart.crud.FindSparePartByCode;
import uo.ri.cws.application.service.spare.sparepart.crud.UpdateSparePart;
import uo.ri.cws.application.util.command.CommandExecutor;

public class SparePartCrudServiceImpl implements SparePartCrudService {

	private CommandExecutor executor = Factory.executor.forExecutor();

	@Override
	public String add(SparePartDto dto) throws BusinessException {
		return executor.execute( new AddSparePart(dto) );
	}

	@Override
	public void delete(String code) throws BusinessException {
		executor.execute( new DeleteSparePart(code) );
	}

	@Override
	public void update(SparePartDto dto) throws BusinessException {
		executor.execute( new UpdateSparePart(dto) );
	}

	@Override
	public Optional<SparePartDto> findByCode(String code) throws BusinessException {
		return executor.execute( new FindSparePartByCode(code) );
	}

}
