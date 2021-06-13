package uo.ri.cws.application.repository;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.domain.SparePart;

public interface SparePartRepository extends Repository<SparePart> {

    	/**
    	 * @param code
    	 * @return the SparePart matching with that code or optional.empty if not
    	 */
	Optional<SparePart> findByCode(String code);

	/**
    	 * @return the SpareParts that are under stock and not pending
    	 */
	List<SparePart> findUnderStockNotPending();
	
	/**
    	 * @return the SpareParts that are under stock
    	 */
	List<SparePart> findUnderStock();

	/**
    	 * @return the SpareParts matching with the description totally or partially
    	 */
	List<SparePart> findByDescription(String description);

}
