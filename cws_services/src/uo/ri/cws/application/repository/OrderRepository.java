package uo.ri.cws.application.repository;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.domain.Order;

public interface OrderRepository extends Repository<Order> {

    	/**
	 * @param orderCode
	 * @return the order identified by the code or optional.empty if there is not
	 */
	Optional<Order> findByCode(String code);

	/**
	 * @param providerNif
	 * @return the orders identified by the nif
	 */
	List<Order> findByProviderNif(String nif);

	/**
	 * @param sparePartCode
	 * @return the orders identified by the code
	 */
	List<Order> findBySparePartCode(String code);

	/**
	 * @return the orders pending
	 */
	List<Order> findOrdersPending();

}
