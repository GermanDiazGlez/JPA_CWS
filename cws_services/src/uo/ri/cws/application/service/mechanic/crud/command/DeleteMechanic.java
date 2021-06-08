package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;

public class DeleteMechanic implements Command<Void> {

	private String mechanicId;

	public DeleteMechanic(String mechanicId) {
		this.mechanicId = mechanicId;
	}

	public Void execute() throws BusinessException {

		MechanicRepository repo = Factory.repository.forMechanic();
		
		Optional<Mechanic> om = repo.findById(mechanicId);
		BusinessChecks.isTrue(om.isPresent(),"Mechanic does not exist");
		Mechanic m = om.get();
		BusinessChecks.isTrue(m.getInterventions().isEmpty(),"Mechanic has interventions");
		
		repo.remove(m);
		
		return null;
	}

}
