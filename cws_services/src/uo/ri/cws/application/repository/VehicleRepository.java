package uo.ri.cws.application.repository;

import java.util.Optional;

import uo.ri.cws.domain.Vehicle;

public interface VehicleRepository extends Repository<Vehicle> {

    	/**
    	 * @param plate
    	 * @return the vehicle matching with that plate or optional.empty if not
    	 */
	Optional<Vehicle> findByPlate(String plate);

}
