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
	
	public Client(String dni, String name, String surname, String email, String phone, Address address) {
		ArgumentChecks.isNotEmpty(dni);
		ArgumentChecks.isNotEmpty(name);
		ArgumentChecks.isNotEmpty(surname);
		ArgumentChecks.isNotEmpty(email);
		ArgumentChecks.isNotEmpty(phone);
		ArgumentChecks.isNotNull(address);
		
		this.dni = dni;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.phone = phone;
		this.address = address;
	}

	public Client(String dni, String name, String surname) {
		this(dni, name, surname, "no@email","no_phone", new Address());
	}

	public Client(String dni) {
		this(dni, "no_name", "no_surname", "no@email","no_phone", new Address());
	}
	
	public Set<PaymentMean> getPaymentMeans() {
		return new HashSet<>(paymentMeans);
	}
	
	Set<PaymentMean> _getPaymentMeans() {
		return paymentMeans;
	}

	public void setPaymentMeans(Set<PaymentMean> paymentMeans) {
		this.paymentMeans = paymentMeans;
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
	
	public Set<Vehicle> getVehicles() {
		return new HashSet<>(vehicles);
	}
	
	Set<Vehicle> _getVehicles() {
		return vehicles;
	}

	public Address getAddress() {
		return address;
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Client [dni=" + dni + ", name=" + name 
			+ ", surname=" + surname + ", email=" + email + ", phone="
			+ phone + ", address=" + address + "]";
	}
}

