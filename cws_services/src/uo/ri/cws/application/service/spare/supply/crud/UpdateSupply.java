package uo.ri.cws.application.service.spare.supply.crud;

import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.SupplyRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.spare.SuppliesCrudService.SupplyDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Supply;

public class UpdateSupply implements Command<Void> {

	private SupplyRepository repo = Factory.repository.forSupply();
	private SupplyDto dto;

	public UpdateSupply(SupplyDto dto) {
		ArgumentChecks.isNotNull( dto );
		ArgumentChecks.isNotNull( dto.provider );
		ArgumentChecks.isNotNull( dto.sparePart );
		this.dto = dto;
	}

	@Override
	public Void execute() throws BusinessException {
		checkValidDto( dto );

		Optional<Supply> os = repo.findByNifAndCode(
				dto.provider.nif,
				dto.sparePart.code
		);
		BusinessChecks.exists( os, "Does not exist such supply" );

		Supply s = os.get();
		BusinessChecks.hasVersion(s, dto.version);

		s.setPrice( dto.price );
		s.setDeliveryTerm( dto.deliveryTerm );

		return null;
	}

	private void checkValidDto(SupplyDto dto) throws BusinessException {
		BusinessChecks.isTrue(dto.price >= 0, "Price must be >= 0");
		BusinessChecks.isTrue(dto.deliveryTerm >= 0, "Devilvery term must be >= 0");
	}

}
