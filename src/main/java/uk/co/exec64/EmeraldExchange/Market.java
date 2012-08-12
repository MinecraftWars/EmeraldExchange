package uk.co.exec64.EmeraldExchange;

import java.io.Serializable;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/*
 * Market contains a list of bids and offers for a single type of item.
 * Each item has its own market that is balanced individually.
 */

public class Market implements Serializable{
	

	private static final long serialVersionUID = 4954437017734705306L;

	private EmeraldExchange plugin;
	
	private Material material;
	public Material getMaterial() { return this.material; }
	public void setMaterial( Material material ) { this.material = material; }
	
	private OrderList bids;
	private OrderList offers;
	
	public Market() {
		this.plugin = null;
		this.material = null;
	}
	
	public Market( EmeraldExchange plugin, Material material ) {
		this.setPlugin(plugin);
		this.material = material;
		bids = new OrderList();
		offers = new OrderList();
	}
	
	//Balance the bids and offers
	public void balance()
	{
		while(isBalanced() == false) {
			
			//Start with the best bid and offer
			
			bids.sort();
			offers.sort();
			
			Order bid = bids.getHighestOrder();
			Order offer = offers.getLowestOrder();
			
			if(offer == null || bid == null)
				return;
			
			//We handle each possible outcome of the two orders separately
			//If the orders cancel each other out then we can remove them both
			
			int stackSize = 64;
			{
				ItemStack stack = new ItemStack(this.material, 1);
				stackSize = stack.getMaxStackSize();
			}
				
			
			if(bid.getQuantity() == offer.getQuantity() ) {
				//Give revenue to the seller
				
				double currentRevenue = plugin.getRevenue(offer.getPlayerName());
				currentRevenue += offer.getPrice();
				plugin.setRevenue(offer.getPlayerName(), currentRevenue);
				
				//Deliver items
				List<EEItemStack> deliveries = plugin.getDeliveries(bid.getPlayerName());
				
				int quantityLeft = offer.getQuantity();
				
				
				//Split the delivery into stacks of 64
				while(quantityLeft > 0) {
					
					if(quantityLeft < stackSize)
						stackSize = quantityLeft;
					
					EEItemStack goods = new EEItemStack(this.material, stackSize);
					deliveries.add(goods);
					
					quantityLeft -= stackSize;
				}
								
				//Remove the orders
				bids.removeOrder(bid);
				offers.removeOrder(offer);
				
			} else if(bid.getQuantity() < offer.getQuantity() ) {
				//Okay, if the bid is smaller than the offer we'll eliminate the bid and reduce the offer
				
				//Reduce the offer by the amount sold
				offer.setQuantity( offer.getQuantity() - bid.getQuantity() );
				offer.setPrice( offer.getPrice() - bid.getPrice());
				
				//Pay the seller
				double currentRevenue = plugin.getRevenue(offer.getPlayerName());
				currentRevenue += bid.getQuantity() * offer.getPrice() / offer.getQuantity();
				plugin.setRevenue(offer.getPlayerName(), currentRevenue);
				
				//Deliver items
				List<EEItemStack> deliveries = plugin.getDeliveries(bid.getPlayerName());
				
				int quantityLeft = bid.getQuantity();
				
				//Split the delivery into stacks of 64
				while(quantityLeft > 0) {
					
					if(quantityLeft < stackSize)
						stackSize = quantityLeft;
					
					EEItemStack goods = new EEItemStack(this.material, stackSize);
					deliveries.add(goods);
					
					quantityLeft -= stackSize;
				}

				//Remove the bid, but leave the offer since it's not completed yet
				bids.removeOrder(bid);
				
			} else if(bid.getQuantity() > offer.getQuantity() ) {
				//The offer is only for part of the bid, so lets remove the offer and reduce the bid
				
				//Reduce the bid by the amount bought
				bid.setQuantity( bid.getQuantity() - offer.getQuantity() );
				bid.setPrice( bid.getPrice() - offer.getPrice());
				
				//Pay the seller
				double currentRevenue = plugin.getRevenue(offer.getPlayerName());
				currentRevenue += offer.getPrice();
				plugin.setRevenue(offer.getPlayerName(), currentRevenue);
				
				//Deliver items				
				List<EEItemStack> deliveries = plugin.getDeliveries(bid.getPlayerName());
				
				int quantityLeft = offer.getQuantity();
				
				//Split the delivery into stacks of 64
				while(quantityLeft > 0) {
					
					if(quantityLeft < stackSize)
						stackSize = quantityLeft;
					
					EEItemStack goods = new EEItemStack(this.material, stackSize);
					deliveries.add(goods);
					
					quantityLeft -= stackSize;
				}

				//Remove the offer, but leave the bid since it's not completed yet
				offers.removeOrder(offer);
			}
			
		}
	}
	
	public Boolean isBalanced()
	{
		if( bids.size() < 1 || offers.size() < 1 )
			return true;
		
		if( bids.getHighestPrice() < offers.getLowestPrice() )
			return true;
		else
			return false;
	}
	
	public void addBid(Order order)
	{
		bids.addOrder(order);
	}
	
	public void addOffer(Order order)
	{
		offers.addOrder(order);
	}
	
	public EmeraldExchange getPlugin() {
		return plugin;
	}
	
	public void setPlugin(EmeraldExchange plugin) {
		this.plugin = plugin;
	}
	
	//Returns how much it would cost to buy quantity
	public double getOfferPrice(int quantity) {
		return offers.getPriceForQuantity(quantity);
	}
	
	//Returns how much you'd make selling quantity
	public double getBidPrice(int quantity) {
		return bids.getPriceForQuantity(quantity);
	}
	
	public int getOffersQuantity() {
		return offers.getTotalQuantity();
	}
	
	public int getBidsQuantity() {
		return bids.getTotalQuantity();
	}
	
	public Object[] getOffers() {
		return offers.getOrders();
	}
	
	public Object[] getBids() {
		return bids.getOrders();
	}

}
