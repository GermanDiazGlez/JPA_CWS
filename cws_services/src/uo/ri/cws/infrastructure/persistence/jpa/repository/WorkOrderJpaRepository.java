package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.List;
import java.util.stream.Collectors;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.domain.WorkOrder;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class WorkOrderJpaRepository 
		extends BaseJpaRepository<WorkOrder> 
		implements WorkOrderRepository {

	@Override
	public List<WorkOrder> findByIds(List<String> idsAveria) {
		return Jpa.getManager()
				.createNamedQuery("WorkOrder.findByIds", WorkOrder.class)
				.setParameter( 1, idsAveria )
				.getResultStream()
	    			.collect(Collectors.toList());
		}

	@Override
	public List<WorkOrder> findByClientDni(String dni) {
	    	return Jpa.getManager()
	    			.createNamedQuery("WorkOrder.findByClientDni", WorkOrder.class)
	    			.getResultStream()
	    			.collect(Collectors.toList());
	}

	@Override
	public List<WorkOrder> findByPlateNumber(String plate) {
		return Jpa.getManager()
				.createNamedQuery("WorkOrder.findByPlateNumber", WorkOrder.class)
				.setParameter( 1, plate )
				.getResultStream()
	    			.collect(Collectors.toList());
	}

}
