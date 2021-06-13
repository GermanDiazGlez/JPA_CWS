package uo.ri.cws.application.repository;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.domain.Provider;

public interface ProviderRepository extends Repository<Provider> {

    	/**
    	 * @param nif
    	 * @return the provider identified by the nif
    	 */
	Optional<Provider> findByNif(String nif);

	/**
    	 * @param name, email, phone
    	 * @return the providers matching with the parameters
    	 */
	List<Provider> findByNameMailPhone(String name, String email, String phone);

	/**
    	 * @param name
    	 * @return the providers matching with the name
    	 */
	List<Provider> findByName(String name);

	/**
    	 * @param sparePartCode
    	 * @return the providers matching with the sparePartCode
    	 */
	List<Provider> findBySparePartCode(String code);

}
