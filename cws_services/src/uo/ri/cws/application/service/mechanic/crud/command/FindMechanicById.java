package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;
import alb.util.assertion.ArgumentChecks;
import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;

public class FindMechanicById implements Command<Optional<MechanicDto>>{

	private String id;

	public FindMechanicById(String id) {
		ArgumentChecks.isNotNull( id , "Id cant be null");
		this.id = id;
	}

	public Optional<MechanicDto> execute() {
		
		MechanicRepository repo = Factory.repository.forMechanic();
		
		return repo.findById(id).map(m -> DtoAssembler.toDto(m));
	}

}
