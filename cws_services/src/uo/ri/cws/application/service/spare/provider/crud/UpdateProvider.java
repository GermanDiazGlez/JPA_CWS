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

public class UpdateProvider implements Command<Void> {

	private ProviderRepository repo = Factory.repository.forProvider();
	private ProviderDto dto;

	public UpdateProvider(ProviderDto dto) {
		ArgumentChecks.isNotNull( dto );
		this.dto = dto;
	}

	@Override
	public Void execute() throws BusinessException {
		checkValidDto( dto );

		Optional<Provider> op = repo.findByNif( dto.nif );
		BusinessChecks.exists( op, "The provider does not exist" );

		checkOthersThanNifNotRepeated( dto );

		Provider p = op.get();
		BusinessChecks.hasVersion(p, dto.version);

		p.setName( dto.name );
		p.setEmail( dto.email );
		p.setPhone( dto.phone );

		return null;
	}

	private void checkValidDto(ProviderDto dto) throws BusinessException {
		BusinessChecks.isNotEmpty( dto.nif, "Nif cannot be empty" );
		BusinessChecks.isNotEmpty( dto.id, "Id cannot be empty" );
		BusinessChecks.isNotEmpty( dto.name, "Name cannot be empty" );
		BusinessChecks.isNotEmpty( dto.email, "Email cannot be empty" );
		BusinessChecks.isTrue( dto.email.contains("@"), "Email has wrong format" );
		BusinessChecks.isNotEmpty( dto.phone, "Phone cannot be empty" );
	}

	private void checkOthersThanNifNotRepeated(ProviderDto dto) throws BusinessException {
		List<Provider> other = repo.findByNameMailPhone(
									dto.name,
									dto.email,
									dto.phone
								);
		BusinessChecks.isTrue( other.isEmpty() );
	}

}
