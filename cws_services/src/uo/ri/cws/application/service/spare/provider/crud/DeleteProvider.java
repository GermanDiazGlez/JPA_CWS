package uo.ri.cws.application.service.spare.provider.crud;

import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ProviderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Provider;

public class DeleteProvider implements Command<Void> {

	private ProviderRepository repo = Factory.repository.forProvider();
	private String nif;

	public DeleteProvider(String nif) {
		ArgumentChecks.isNotEmpty(nif);
		this.nif = nif;
	}

	@Override
	public Void execute() throws BusinessException {
		Optional<Provider> op = repo.findByNif(nif);

		BusinessChecks.exists( op, "There is no provider with that nif" );
		Provider p = op.get();
		BusinessChecks.isTrue( p.getSupplies().isEmpty(), "The provider has dependencies" );
		BusinessChecks.isTrue( p.getOrders().isEmpty(), "The provider has dependencies" );

		repo.remove( p );

		return null;
	}

}
