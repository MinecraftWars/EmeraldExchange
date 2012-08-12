package uk.co.exec64.EmeraldExchange;

import java.io.Serializable;

/*
 * An order represents a bid/offer. It has a quantity remaining and a price for each item.
 */

public class Order implements Comparable<Order>, Serializable {
	

	private static final long serialVersionUID = 5814940485148858462L;

	public Order(int quantity, double price, String player) {
		this.quantity = quantity;
		this.price = price;
		this.player = player;
	}
	
	private int quantity;
	private double price;
	private String player;
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}

	public String getPlayerName() {
		return player;
	}

	public void setPlayerName(String player) {
		this.player = player;
	}


	@Override
	public int compareTo(Order order) {
		double difference = this.getPrice() / this.getQuantity() - order.getPrice() / order.getQuantity();
		
		if( difference < 0)
			return -1;
		else if( difference > 0 )
			return 1;
		else
			return 0;
	}
}
