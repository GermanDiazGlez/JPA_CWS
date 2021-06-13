package uo.ri.cws.application.repository;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.domain.Supply;

public interface SupplyRepository extends Repository<Supply> {

    	/**
    	 * @param nif
    	 * @param code
    	 * @return the supply matching with the nif and code or optional.empty if not
    	 */
	Optional<Supply> findByNifAndCode(String nif, String code);

	/**
    	 * @param nif
    	 * @return the supplies matching with the nif 
    	 */
	List<Supply> findByProviderNif(String nif);

	/**
    	 * @param sparePartCode
    	 * @return the supplies matching with the sparePartCode 
    	 */
	List<Supply> findBySparePartCode(String code);

}
