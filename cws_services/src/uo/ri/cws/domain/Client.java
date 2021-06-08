package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;

@Entity
@Table(name = "TCLIENTS")
public class Client extends BaseEntity{
	@Column(unique = true) private String dni;
	@Basic(optional = false)// no puede ser null
	private String name;
	private String surname;
	private String email;
	private String phone;
	private Address address;
	
	//Atributos accidentales
	@OneToMany (mappedBy="client") private Set<Vehicle> vehicles = new HashSet<>();
	
	@OneToMany (mappedBy="client") private Set<PaymentMean> paymentMeans = new HashSet<>();
	
	Client(){}
	
	public Client(String dni) {
		super();
		ArgumentChecks.isNotEmpty(dni);
		this.dni = dni;
	}
	
	public Client(String dni, String name, String surname) {
		this(dni);
		ArgumentChecks.isNotEmpty(name);
		ArgumentChecks.isNotEmpty(surname);
		this.name = name;
		this.surname = surname;
	}

	public String getDni() {
		return dni;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Client [dni=" + dni + ", name=" + name + ", surname=" + surname + ", email=" + email + ", phone="
				+ phone + ", address=" + address + "]";
	}

	Set<Vehicle> _getVehicles() {
		return vehicles;
	}
	
	public Set<Vehicle> getVehicles() {
		return new HashSet<>(vehicles);
	}

	Set<PaymentMean> _getPaymentMeans() {
		return paymentMeans;
	}
	
	public Set<PaymentMean> getPaymentMeans() {
		return new HashSet<>(paymentMeans);
	}
	
	
	
}

