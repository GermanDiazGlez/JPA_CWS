package uo.ri.cws.application.service.spare.supply.crud;

import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.SupplyRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Supply;

public class DeleteSupply implements Command<Void> {

	private SupplyRepository repo = Factory.repository.forSupply();
	private String nif;
	private String code;

	public DeleteSupply(String nif, String code) {
		ArgumentChecks.isNotEmpty(nif);
		ArgumentChecks.isNotEmpty(code);
		this.nif = nif;
		this.code = code;
	}

	@Override
	public Void execute() throws BusinessException {
		Optional<Supply> os = repo.findByNifAndCode(nif, code);
		BusinessChecks.exists( os, "Does not exist such supply" );

		Supply s = os.get();
		repo.remove( s );

		return null;
	}

}
