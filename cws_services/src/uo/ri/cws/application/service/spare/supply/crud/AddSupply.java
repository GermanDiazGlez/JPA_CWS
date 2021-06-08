package uo.ri.cws.application.service.spare.supply.crud;

import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ProviderRepository;
import uo.ri.cws.application.repository.SparePartRepository;
import uo.ri.cws.application.repository.SupplyRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.spare.SuppliesCrudService.SupplyDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Provider;
import uo.ri.cws.domain.SparePart;
import uo.ri.cws.domain.Supply;

public class AddSupply implements Command<String> {

	private ProviderRepository provRepo = Factory.repository.forProvider();
	private SparePartRepository spareRepo = Factory.repository.forSparePart();
	private SupplyRepository repo = Factory.repository.forSupply();
	private SupplyDto dto;

	public AddSupply(SupplyDto dto) {
		ArgumentChecks.isNotNull( dto );
		ArgumentChecks.isNotNull( dto.provider );
		ArgumentChecks.isNotNull( dto.sparePart );
		this.dto = dto;
	}

	@Override
	public String execute() throws BusinessException {
		checkValidDto( dto );
		checkNotRepeated( dto.provider.nif, dto.sparePart.code );

		Optional<Provider> op = provRepo.findByNif( dto.provider.nif );
		BusinessChecks.exists( op );

		Optional<SparePart> osp = spareRepo.findByCode( dto.sparePart.code );
		BusinessChecks.exists( osp );

		Provider p = op.get();
		SparePart sp = osp.get();
		Supply s = new Supply(p, sp, dto.price, dto.deliveryTerm);
		repo.add( s );

		return s.getId();
	}

	private void checkNotRepeated(String nif, String code) throws BusinessException {
		Optional<Supply> os = repo.findByNifAndCode(nif, code);
		BusinessChecks.isTrue( os.isEmpty(),
				"There already exist a supply for that provider and spare part"
			);
	}

	private void checkValidDto(SupplyDto dto) throws BusinessException {
		BusinessChecks.isTrue( dto.price >= 0, "Price nust be >= 0" );
		BusinessChecks.isTrue( dto.deliveryTerm >= 0, "Delivery term nust be >= 0" );
		BusinessChecks.isNotEmpty( dto.provider.nif, "Provider nif cannot be empty" );
		BusinessChecks.isNotEmpty( dto.sparePart.code, "Spare code cannot be empty" );
	}

}
