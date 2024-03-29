package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;

@Entity
@Table(name = "TVEHICLETYPES")
public class VehicleType extends BaseEntity{
	// natural attributes
	@Column(unique=true) private String name;
	private double pricePerHour;

	// accidental attributes
	@OneToMany (mappedBy="vehicleType") private Set<Vehicle> vehicles = new HashSet<>();

	VehicleType() {}

	public VehicleType(String name) {
		super();
		ArgumentChecks.isNotEmpty(name);
		this.name = name;
	}


	public VehicleType(String name, double pricePerHour) {
		this(name);
		ArgumentChecks.isNotNull(pricePerHour);
		this.pricePerHour = pricePerHour;
	}

	@Override
	public String toString() {
		return "VehicleType [name=" + name + ", pricePerHour=" + pricePerHour + "]";
	}

	
	
	public String getName() {
		return name;
	}

	public double getPricePerHour() {
		return pricePerHour;
	}

	public Set<Vehicle> getVehicles() {
		return new HashSet<>( vehicles );
	}

	Set<Vehicle> _getVehicles() {
		return vehicles;
	}

}
