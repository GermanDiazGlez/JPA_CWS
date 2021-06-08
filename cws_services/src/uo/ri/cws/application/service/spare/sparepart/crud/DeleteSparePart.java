package uo.ri.cws.application.service.spare.sparepart.crud;

import java.util.List;
import java.util.Optional;

import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.OrderRepository;
import uo.ri.cws.application.repository.SparePartRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Order;
import uo.ri.cws.domain.SparePart;

public class DeleteSparePart implements Command<Void> {

	private SparePartRepository repo = Factory.repository.forSparePart();
	private String code;

	public DeleteSparePart(String code) {
		ArgumentChecks.isNotNull( code );
		this.code = code;
	}

	@Override
	public Void execute() throws BusinessException {
		Optional<SparePart> op = repo.findByCode(code);

		BusinessChecks.exists( op, "There is no spare part with that code" );
		SparePart p = op.get();
		checkDoesNoHaveDependecies( p );

		repo.remove( p );

		return null;
	}

	private void checkDoesNoHaveDependecies(SparePart p) throws BusinessException {
		BusinessChecks.isTrue( p.getSupplies().isEmpty(),
				"The spare has dependencies"
			);
		BusinessChecks.isTrue( p.getSubstitutions().isEmpty(),
				"The spare has substitutions"
			);

		OrderRepository repo = Factory.repository.forOrder();
		List<Order> orders = repo.findBySparePartCode( p.getCode() );
		BusinessChecks.isTrue( orders.isEmpty(), "The spare has dependencies");
	}

}
