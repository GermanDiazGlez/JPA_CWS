package uo.ri.cws.domain;


import alb.util.assertion.ArgumentChecks;

public class OrderLine {

	public enum OrderStatus { PENDING, RECEIVED }
	
	private int quantity;
	private double price;
	
	private SparePart sparePart;
	
	OrderLine(){}
	
	public OrderLine(int quantity, double price, SparePart sparePart, Order order) {
		ArgumentChecks.isTrue(sparePart.getStock() <= sparePart.getMinStock());
		ArgumentChecks.isNotNull(sparePart);
		ArgumentChecks.isTrue(quantity >= 0);
		ArgumentChecks.isTrue(price >= 0);
		
		this.quantity = quantity;
		this.price = price;
		this.sparePart = sparePart;
	}

	public OrderLine(SparePart sparePart, double price) {

		this(sparePart.getMaxStock() - sparePart.getStock(),price,sparePart,null);
	}

	public int getQuantity() {
		return quantity;
	}

	public double getPrice() {
		return price;
	}

	public SparePart getSparePart() {
		return sparePart;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + quantity;
		result = prime * result + ((sparePart == null) ? 0 : sparePart.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderLine other = (OrderLine) obj;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (quantity != other.quantity)
			return false;
		if (sparePart == null) {
			if (other.sparePart != null)
				return false;
		} else if (!sparePart.equals(other.sparePart))
			return false;
		return true;
	}
	
	

	@Override
	public String toString() {
		return "OrderLine [quantity=" + quantity + ", price=" + price 
			+ ", sparePart=" + sparePart + "]";
	}

	void _setQuantity(int i) {
		quantity = i;
	}

	public double getAmount() {
		return price * quantity;
	}

	public void receive() {
		sparePart.updatePriceAndStock(price, quantity);
		
	}

}
