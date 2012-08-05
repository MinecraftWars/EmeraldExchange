package uk.co.exec64.EmeraldExchange;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class OrderList implements Serializable {
	
	private static final long serialVersionUID = -9196319325323988200L;
	
	private ArrayList<Order> orderList;
	
	public OrderList()
	{
		orderList = new ArrayList<Order>();
	}
	
	public void addOrder( Order order) {
		orderList.add(order);		
		sort();
	}
	
	public void removeOrder( Order order ) {
		orderList.remove(order);
	}
	
	public void sort() {
		Collections.sort(orderList);
	}
	
	public int size() {
		return orderList.size();
	}
	
	public double getHighestPrice()
	{
		sort();
		
		if(orderList.size() > 0) {
			Order order = orderList.get( orderList.size() - 1 );
			return order.getPrice() / order.getQuantity();
		}
		else
			return 0;
	}
	
	public double getLowestPrice()
	{
		sort();
		
		if(orderList.size() > 0) {
			Order order = orderList.get( 0 );
			return order.getPrice() / order.getQuantity();
		}
		else
			return 0;
	}
	
	//Returns the order with the highest price
	public Order getHighestOrder() {
		sort();
		
		Order order = null;
		
		if(orderList.size() > 0) {
			order = orderList.get( orderList.size() - 1 );
		}
		
		return order;
	}
	
	//Returns the order with the lowest price
	public Order getLowestOrder() {
		sort();
		
		Order order = null;
		
		if(orderList.size() > 0) {
			order = orderList.get( 0 );
		}
		
		return order;
	}
	
	public int getTotalQuantity()
	{
		int quantity = 0;
		for( Object object : orderList.toArray() ) {
			Order order = (Order)object;
			quantity += order.getQuantity();
		}
		
		return quantity;
		
	}
	
	public double getPriceForQuantity( int quantity ) {
		double price = 0;
		for( Object object : orderList.toArray() ) {
			Order order = (Order)object;
			
			if( quantity > 0 ) {
				if(quantity >= order.getQuantity()) {
					price += order.getPrice();
					quantity -= order.getQuantity();
				} else if(quantity < order.getQuantity()) {
					price += quantity * (order.getPrice() / order.getQuantity());
					quantity = 0;
				}
			}
		}
		
		return price;
	}
	
	public Object[] getOrders() {
		return orderList.toArray();
	}
}
