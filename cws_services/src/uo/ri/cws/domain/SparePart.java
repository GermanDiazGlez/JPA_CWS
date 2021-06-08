package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;

@Entity
@Table(name = "TSPAREPARTS")
public class SparePart extends BaseEntity{
	// natural attributes
	@Column (unique = true)
	private String code;
	private String description;
	private double price;
	
	int stock;
	int minStock;
	int maxStock;

	// accidental attributes
	@OneToMany (mappedBy="sparePart") private Set<Substitution> substitutions = new HashSet<>();

	// accidental attributes
	@OneToMany (mappedBy="sparePart", fetch=FetchType.EAGER) private Set<Supply> supplies = new HashSet<>();
		
		
	SparePart() {}
	
	public SparePart(String code) {
		super();
		ArgumentChecks.isNotEmpty(code);
		this.code = code;
	}
	
	public SparePart(String code, String description, double price) {
		this(code);
		ArgumentChecks.isNotEmpty(description);
		ArgumentChecks.isNotNull(price);
		ArgumentChecks.isTrue(price>=0, "Price can not be negative");
		this.description = description;
		this.price = price;
	}
	
	public SparePart(String code, String description, double price, Set<Substitution> substitutions) {
		this(code, description, price);
		ArgumentChecks.isNotNull(substitutions);
		this.substitutions = substitutions;
	}
	
	public SparePart(String code, String description, double price, Set<Substitution> substitutions, Set<Supply> supplies) {
		this(code, description, price);
		ArgumentChecks.isNotNull(supplies);
		ArgumentChecks.isNotNull(substitutions);
		this.supplies = supplies;
		this.substitutions = substitutions;
	}

	public SparePart(String code, String description, double price, int stock, int minStock, int maxStock) {
		this(code, description, price);
		ArgumentChecks.isNotNull(stock);
		ArgumentChecks.isNotNull(minStock);
		ArgumentChecks.isNotNull(maxStock);
		ArgumentChecks.isTrue(minStock>=0, "MinStock can not be negative");
		ArgumentChecks.isTrue(maxStock>=0, "MaxStock can not be negative");
		ArgumentChecks.isTrue(stock>=0, "Stock can not be negative");
		this.stock = stock;
		this.minStock = minStock;
		this.maxStock = maxStock;
	}

	public Set<Substitution> getSubstitutions() {
		return new HashSet<>( substitutions );
	}

	Set<Substitution> _getSubstitutions() {
		return substitutions;
	}
	
	/*
	 * Estos dos métodos van por duplicado debido a un error en los tests en los que en una parte se llama a getSubstitutions() y en otra a getSustitutions()
	 */
	public Set<Substitution> getSustitutions() {
		return new HashSet<>( substitutions );
	}

	Set<Substitution> _getSustitutions() {
		return substitutions;
	}
	
	public Set<Supply> getSupplies() {
		return new HashSet<>( supplies );
	}

	Set<Supply> _getSupplies() {
		return supplies;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public double getPrice() {
		return price;
	}
	
	public void setCode(String code) {
		this.code = code;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public void setMinStock(int minStock) {
		this.minStock = minStock;
	}

	public void setMaxStock(int maxStock) {
		this.maxStock = maxStock;
	}

	public int getStock() {
		return stock;
	}

	public int getMinStock() {
		return minStock;
	}

	public int getMaxStock() {
		return maxStock;
	}

	@Override
	public String toString() {
		return "SparePart [code=" + code + ", description=" + description + ", price=" + price + "]";
	}

	public int getQuantityToOrder() {
		if(stock==minStock || stock==maxStock || stock>minStock) {
			return 0;
		}
		return stock<minStock ? this.maxStock - this.stock : 0;
	}

	public int getTotalUnitsSold() {
		int totalUnitsSold = 0;
		for (Substitution substitution : substitutions) {
			totalUnitsSold += substitution.getQuantity();
		}
		return totalUnitsSold;
	}

	public void updatePriceAndStock(double purchasePrice, int newQuantity) {
		ArgumentChecks.isTrue(newQuantity!=0, "new quantity cant be zero");
		ArgumentChecks.isTrue(purchasePrice>=0, "puechase price cant be negative");
		int previousStock = getStock();
		double previousPrice = getPrice();
		int maxStock = getStock() + newQuantity;
		price = (previousStock * previousPrice
				+ 1.2 * purchasePrice * (maxStock - previousStock)
			)
			/ maxStock;

		stock = getStock() + newQuantity;
	}

	public Optional<Supply> getBestSupply() {
		double price = Double.MAX_VALUE;
		Supply s = new Supply();
		for (Supply supply : this.supplies) {
			if(supply.getPrice() < price) {
				price = supply.getPrice();
				s = supply;
			}
		}
		if(s.getSparePart() == null) {
			return Optional.empty();
		}
		return Optional.of(s);
	}
	
}
