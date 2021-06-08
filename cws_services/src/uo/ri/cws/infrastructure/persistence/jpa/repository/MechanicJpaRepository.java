package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.Optional;

import javax.persistence.EntityManager;

import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class MechanicJpaRepository extends BaseJpaRepository<Mechanic> implements MechanicRepository {

    @Override
    public Optional<Mechanic> findByDni(String dni) {
	EntityManager em = Jpa.getManager();
	return em.createNamedQuery("Mechanic.findByDni", Mechanic.class).setParameter(1, dni).getResultStream()
		.findFirst();
    }

}
