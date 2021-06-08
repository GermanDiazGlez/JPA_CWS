package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.domain.PaymentMean;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class PaymentMeanJpaRepository
		extends BaseJpaRepository<PaymentMean> 
		implements PaymentMeanRepository {

	@Override
	public List<PaymentMean> findPaymentMeansByClientId(Long id) {
		EntityManager em = Jpa.getManager();
		return em.createNamedQuery("PaymentMean.findPaymentMeansByClientId", PaymentMean.class)
			.setParameter(1, id)
			.getResultStream()
			.collect(Collectors.toList());
	}

	@Override
	public List<PaymentMean> findPaymentMeansByClientDni(String dni) {
		EntityManager em = Jpa.getManager();
		return em.createNamedQuery("PaymentMean.findPaymentMeansByClientDni", PaymentMean.class)
			.setParameter(1, dni)
			.getResultStream()
			.collect(Collectors.toList());
	}

}
