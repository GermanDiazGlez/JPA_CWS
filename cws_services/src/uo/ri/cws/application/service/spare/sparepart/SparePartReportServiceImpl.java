package uo.ri.cws.application.service.spare.sparepart;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.spare.SparePartReportService;
import uo.ri.cws.application.service.spare.sparepart.report.FindSparePartReportByCode;
import uo.ri.cws.application.service.spare.sparepart.report.FindSparePartsReportByDescription;
import uo.ri.cws.application.service.spare.sparepart.report.FindSparePartsUnderStock;
import uo.ri.cws.application.util.command.CommandExecutor;

public class SparePartReportServiceImpl implements SparePartReportService {

	private CommandExecutor executor = Factory.executor.forExecutor();

	@Override
	public Optional<SparePartReportDto> findByCode(String code) throws BusinessException {
		return executor.execute( new FindSparePartReportByCode( code ) );
	}

	@Override
	public List<SparePartReportDto> findByDescription(String description) throws BusinessException {
		return executor.execute( new FindSparePartsReportByDescription( description ) );
	}

	@Override
	public List<SparePartReportDto> findUnderStock() throws BusinessException {
		return executor.execute( new FindSparePartsUnderStock() );
	}

}
