package uo.ri.cws.application.service.spare.sparepart.crud;

import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.SparePartRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.spare.SparePartCrudService.SparePartDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.SparePart;

public class UpdateSparePart implements Command<Void> {

	private SparePartRepository repo = Factory.repository.forSparePart();
	private SparePartDto dto;

	public UpdateSparePart(SparePartDto dto) {
		ArgumentChecks.isNotNull( dto );
		this.dto = dto;
	}

	@Override
	public Void execute() throws BusinessException {
		checkValidDto( dto );

		Optional<SparePart> os = repo.findByCode( dto.code );
		BusinessChecks.exists( os, "The spare does not exist");
		SparePart sp = os.get();

		BusinessChecks.hasVersion(sp, dto.version);

		sp.setDescription( dto.description );
		sp.setPrice( dto.price );
		sp.setStock( dto.stock );
		sp.setMinStock( dto.minStock );
		sp.setMaxStock( dto.maxStock );

		return null;
	}

	private void checkValidDto(SparePartDto dto) throws BusinessException {
		BusinessChecks.isNotEmpty(dto.code, "Code cannot be empty");
		BusinessChecks.isNotEmpty(dto.id, "Id cannot be empty");
		// code ignored
		BusinessChecks.isNotEmpty(dto.description, "Description cannot be empty");
		BusinessChecks.isTrue(dto.price >= 0, "price cannot be negative");
		BusinessChecks.isTrue(dto.stock >= 0, "stock cannot be negative");
		BusinessChecks.isTrue(dto.minStock >= 0, "min stock cannot be negative");
		BusinessChecks.isTrue(dto.maxStock >= 0, "max stock cannot be negative");
	}

}
