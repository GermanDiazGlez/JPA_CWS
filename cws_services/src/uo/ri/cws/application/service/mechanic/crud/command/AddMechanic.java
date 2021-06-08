package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;

public class AddMechanic implements Command<MechanicDto> {

	private MechanicDto dto;

	public AddMechanic(MechanicDto dto) {
		this.dto = dto;
	}

	public MechanicDto execute() throws BusinessException {
		
		validateMechanic();
		MechanicRepository repo = Factory.repository.forMechanic();
		
		Optional<Mechanic> om = repo.findByDni(dto.dni);
	
		BusinessChecks.isTrue(om.isEmpty(),"Mechanic already exists");
		
		Mechanic m = new Mechanic(dto.dni, dto.name, dto.surname);
		
		dto.id = m.getId();
		
		repo.add(m);
		
		return dto;
	}
	
	
	public void validateMechanic() throws BusinessException {
		BusinessChecks.isTrue(dto.dni!=null, "Mechanic cant be null");
	}

}
