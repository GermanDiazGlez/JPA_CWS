package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.List;
import java.util.stream.Collectors;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;

public class FindAllMechanics implements Command<List<MechanicDto>>{

	public List<MechanicDto> execute() {
		
		MechanicRepository repo = Factory.repository.forMechanic();
		
		return repo.findAll().stream().map(DtoAssembler::toDto).collect(Collectors.toList());
	}

}
