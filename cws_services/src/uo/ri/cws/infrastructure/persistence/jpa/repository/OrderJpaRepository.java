package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import uo.ri.cws.application.repository.OrderRepository;
import uo.ri.cws.domain.Order;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class OrderJpaRepository extends BaseJpaRepository<Order> implements OrderRepository {

	@Override
	public Optional<Order> findByCode(String code) {
		EntityManager em = Jpa.getManager();
		return em.createNamedQuery("Order.findByCode", Order.class)
			.setParameter(1, code)
			.getResultStream()
			.findFirst();
	}

	@Override
	public List<Order> findByProviderNif(String nif) {
		EntityManager em = Jpa.getManager();
		return em.createNamedQuery("Order.findByProviderNif", Order.class)
			.setParameter(1, nif)
			.getResultStream()
			.collect(Collectors.toList());
	}

	@Override
	public List<Order> findBySparePartCode(String code) {
		EntityManager em = Jpa.getManager();
		return em.createNamedQuery("Order.findBySparePartCode", Order.class)
			.setParameter(1, code)
			.getResultStream()
			.collect(Collectors.toList());
	}

	@Override
	public List<Order> findOrdersPending() {
		EntityManager em = Jpa.getManager();
		return em.createNamedQuery("Order.findOrdersPending", Order.class)
			.getResultStream()
			.collect(Collectors.toList());
	}



}
