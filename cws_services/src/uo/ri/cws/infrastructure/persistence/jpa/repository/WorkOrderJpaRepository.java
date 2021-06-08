package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

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
				.getResultList();
		}

	@Override
	public List<WorkOrder> findByClientDni(String dni) {
		EntityManager em = Jpa.getManager();
		return em.createNamedQuery("WorkOrder.findByClientDni", WorkOrder.class)
			.getResultStream()
			.collect(Collectors.toList());
	}

	@Override
	public List<WorkOrder> findByPlateNumber(String plate) {
		EntityManager em = Jpa.getManager();
		return em.createNamedQuery("WorkOrder.findByPlateNumber", WorkOrder.class)
			.getResultStream()
			.collect(Collectors.toList());
		//??????????
	}

}
