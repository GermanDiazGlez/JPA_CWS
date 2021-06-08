package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;

@Entity
@Table(name = "TPROVIDERS")
public class Provider extends BaseEntity{
	@Column (unique = true)
	public String nif;
	public String name;
	public String email;
	public String phone;
	
	// accidental attributes
	@OneToMany (mappedBy="provider", fetch=FetchType.EAGER) private Set<Supply> supplies = new HashSet<>();
	
	@OneToMany (mappedBy="provider") private Set<Order> orders = new HashSet<>();

	Provider() {}
	
	public Provider(String nif) {
		super();
		ArgumentChecks.isNotNull(nif);
		this.nif = nif;
	}
	
	public Provider(String nif, String name, String email, String phone) {
		this(nif);
		ArgumentChecks.isNotNull(name);
		ArgumentChecks.isNotNull(email);
		ArgumentChecks.isNotNull(phone);
		this.name = name;
		this.email = email;
		this.phone = phone;
	}
	

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public Set<Supply> getSupplies() {
		return new HashSet<>( supplies );
	}

	Set<Supply> _getSupplies() {
		return supplies;
	}
	
	public Set<Order> getOrders() {
		return new HashSet<>( orders );
	}

	Set<Order> _getOrders() {
		return orders;
	}

}
