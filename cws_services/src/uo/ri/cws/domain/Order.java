package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import alb.util.assertion.ArgumentChecks;
import uo.ri.cws.domain.base.BaseEntity;

@Entity
@Table(name = "TORDERS")
public class Order extends BaseEntity{
    	@Column (unique = true)
	private String code;
	private double amount;
	private LocalDate orderedDate;
	private LocalDate receptionDate;
	
	public enum OrderStatus { PENDING, RECEIVED }
	private OrderStatus status;
	
	@ManyToOne private Provider provider;
	
	@ElementCollection
	@CollectionTable(name="TOrderLines")
	private Set<OrderLine> lines = new HashSet<>();
	
	Order(){}
	
	public Order(String code, double amount, LocalDate orderedDate, LocalDate receptionDate, OrderStatus status) {
		ArgumentChecks.isNotEmpty(code);
		ArgumentChecks.isTrue(amount >= 0);
		ArgumentChecks.isNotNull(orderedDate);
		ArgumentChecks.isNotNull(receptionDate);
		ArgumentChecks.isNotNull(status);
		
		this.code = code;
		this.amount = amount;
		this.orderedDate = orderedDate;
		this.receptionDate = receptionDate;
		this.status = status;
	}

	public Order(String code) {
		this(code, 0, LocalDate.now(), LocalDate.now(), OrderStatus.PENDING);
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
	}

	public boolean isReceived() {
		if (status.equals(OrderStatus.RECEIVED))
			return true;
		return false;
	}

	public boolean isPending() {
		if (status.equals(OrderStatus.PENDING))
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
	

	public String getCode() {
		return code;
	}

	public double getAmount() {
		return amount;
	}

	public LocalDate getOrderedDate() {
		return orderedDate;
	}

	public LocalDate getReceptionDate() {
		return receptionDate;
	}

	public OrderStatus getStatus() {
		return status;
	}
	
	public Provider getProvider() {
		return provider;
	}

	@Override
	public String toString() {
		return "Order [code=" + code + ", amount=" + amount + ", orderedDate=" 
				+ orderedDate + ", receptionDate="
				+ receptionDate + ", status=" + status + "]";
	}

	void _setProvider(Provider provider) {
		this.provider = provider;
	}
}
