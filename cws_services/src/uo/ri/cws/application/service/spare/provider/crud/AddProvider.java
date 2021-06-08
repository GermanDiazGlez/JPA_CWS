package uo.ri.cws.application.service.spare.provider.crud;

import java.util.List;
import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ProviderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.spare.ProvidersCrudService.ProviderDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Provider;

public class AddProvider implements Command<String> {

	private ProviderRepository repo = Factory.repository.forProvider();
	private ProviderDto dto;

	public AddProvider(ProviderDto dto) {
		ArgumentChecks.isNotNull( dto );
		this.dto = dto;
	}

	@Override
	public String execute() throws BusinessException {
		checkValidDto( dto );
		checkUniqueNif( dto.nif );
		checkOthersThanNifNotRepeated( dto );

		Provider p = new Provider(dto.nif, dto.name, dto.email, dto.phone);
		repo.add( p );

		return p.getId();
	}

	private void checkOthersThanNifNotRepeated(ProviderDto dto) throws BusinessException {
		List<Provider> other = repo.findByNameMailPhone(
				dto.name, dto.email, dto.phone);

		BusinessChecks.isTrue( other.isEmpty(),
				"There is another provider with same name, email and phone"
			);
	}

	private void checkUniqueNif(String nif) throws BusinessException {
		Optional<Provider> op = repo.findByNif( nif );

		BusinessChecks.isTrue( op.isEmpty(),
				"There is another provider with that nif"
			);
	}

	private void checkValidDto(ProviderDto dto) throws BusinessException {
		BusinessChecks.isNotEmpty( dto.nif, "Nif cannot be empty" );
		BusinessChecks.isNotEmpty( dto.name, "Nane cannot be empty" );
		BusinessChecks.isNotEmpty( dto.email, "Email cannot be empty" );
		BusinessChecks.isTrue( dto.email.contains("@"), "Email has wrong format" );
		BusinessChecks.isNotEmpty( dto.phone, "Phone cannot be empty" );
	}

}
