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

public class AddSparePart implements Command<String> {

	private SparePartRepository repo = Factory.repository.forSparePart();
	private SparePartDto dto;

	public AddSparePart(SparePartDto dto) {
		ArgumentChecks.isNotNull( dto );
		this.dto = dto;
	}

	@Override
	public String execute() throws BusinessException {
		checkValidDto( dto );
		checkNotRepeatedCode( dto.code );

		SparePart sp = new SparePart(
				dto.code, dto.description, dto.price,
				dto.stock, dto.minStock, dto.maxStock
			);
		repo.add( sp );

		return sp.getId();
	}

	private void checkNotRepeatedCode(String code) throws BusinessException {
		Optional<SparePart> os = repo.findByCode(code);
		BusinessChecks.isFalse( os.isPresent() );
	}

	private void checkValidDto(SparePartDto dto) throws BusinessException {
		BusinessChecks.isNotEmpty(dto.code, "Code cannot be empty");
		BusinessChecks.isNotEmpty(dto.description, "Description cannot be empty");
		BusinessChecks.isTrue(dto.price >= 0, "price cannot be negative");
		BusinessChecks.isTrue(dto.stock >= 0, "stock cannot be negative");
		BusinessChecks.isTrue(dto.minStock >= 0, "min stock cannot be negative");
		BusinessChecks.isTrue(dto.maxStock >= 0, "max stock cannot be negative");
	}

}
