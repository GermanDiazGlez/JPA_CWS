package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;

@Entity
@Table(name = "TORDERS")
public class Order extends BaseEntity{
	
	@Column (unique = true)
	private String code;
	private LocalDate orderedDate;
	private LocalDate receptionDate;
	private double amount;

	
	public enum OrderStatus { PENDING, RECEIVED }
	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	//Atributos accidentales
	@ManyToOne private Provider provider;
	
	@ElementCollection
	@CollectionTable(name="TOrderLines")
	private Set<OrderLine> lines = new HashSet<>();
	
	public Order() {}

	public Order(String code, double amount, LocalDate orderedDate, LocalDate receptionDate, OrderStatus status) {
		
		ArgumentChecks.isNotEmpty(code);
		ArgumentChecks.isNotNull(orderedDate);
		ArgumentChecks.isNotNull(receptionDate);
		ArgumentChecks.isNotNull(status);
		ArgumentChecks.isTrue(amount >= 0);
		
		this.code = code;
		this.amount = amount;
		this.orderedDate = orderedDate;
		this.receptionDate = receptionDate;
		this.status = status;
	}
	
	public Order(String code) {
		ArgumentChecks.isNotNull(code);
		this.code = code;
		this.orderedDate = LocalDate.now();
		this.status = OrderStatus.PENDING;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public LocalDate getOrderedDate() {
		return orderedDate;
	}

	public void setOrderedDate(LocalDate orderedDate) {
		this.orderedDate = orderedDate;
	}

	public LocalDate getReceptionDate() {
		return receptionDate;
	}

	public void setReceptionDate(LocalDate receptionDate) {
		this.receptionDate = receptionDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public Provider getProvider() {
		return provider;
	}

	void _setProvider(Provider provider) {
		this.provider = provider;
	}

	public Set<OrderLine> getLines() {
		return new HashSet<> ( lines );
	}
	
	

	public void setLines(Set<OrderLine> lines) {
		this.lines = lines;
	}
	

//	private double calculateNewPrice(OrderLine ol) {
//		int previousStock = ol.getSparePart().getStock();
//		int maxStock = ol.getSparePart().getMaxStock();
//		double purchasePrice = ol.getPrice();
//		double previousPrice = ol.getSparePart().getPrice();
//		
//		double newPrice = (previousStock * previousPrice
//				+ 1.2 * purchasePrice * (maxStock - previousStock)
//			)
//			/ maxStock;
//		return newPrice;
//	}
	
	private double calculateAmount() {
		double amount = 0;
		for (OrderLine orderLine : lines) {
			amount = orderLine.getPrice() * orderLine.getQuantity();
		}
		return amount;
	}
	
	public void setAmount() {
		this.amount = calculateAmount();
	}

	
	public void addSparePartFromSupply(Supply supply) {
		
		ArgumentChecks.isNotNull(supply);
		
		for (OrderLine line : lines) {
			if (line.getSparePart().equals(supply.getSparePart())) {
				throw new IllegalStateException();
			}	
		}
		
		if (supply.getSparePart().getStock() < supply.getSparePart().getMinStock()) {
			int amount = supply.getSparePart().getMaxStock() - supply.getSparePart().getStock();
			lines.add(new OrderLine(amount,supply.getPrice(), supply.getSparePart(), this));
			updateAmount();
		}
			
	}
	

	private void updateAmount() {
		amount = 0;
		
		for (OrderLine line : lines)
			amount += line.getAmount();
	}

	public Set<OrderLine> getOrderLines() {
		return new HashSet<>(lines);
	}
	
	Set<OrderLine> _getOrderLines() {
		return lines;
	}

	public void receive() {
		if (status.equals(OrderStatus.RECEIVED))
			throw new IllegalStateException();
		for (OrderLine line : lines)
			line.receive();

		status = OrderStatus.RECEIVED;
		this.receptionDate = LocalDate.now();
	}

	public boolean isReceived() {
		if (status.equals(OrderStatus.RECEIVED))
			return true;
		return false;
	}

	public void removeSparePart(SparePart sp2) {
		ArgumentChecks.isNotNull(sp2);
		
		OrderLine toRemove = null;
		
		for (OrderLine line : lines)
			if (line.getSparePart().equals(sp2))
				toRemove = line;
		
		lines.remove(toRemove);
				
		
	}

	public boolean isPending() {
		if (status.equals(OrderStatus.PENDING))
			return true;
		return false;
	}
	
	
	
}
