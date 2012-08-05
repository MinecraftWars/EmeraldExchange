package uk.co.exec64.EmeraldExchange;

import org.bukkit.ChatColor;

public class Conf {

	//Tax on revenue, 0.2 = 20% tax, 0.05 = 5% tax.
	public static double tax = 0.1;
	
	//Cost to add a new bid/offer
	public static double listingFee = 0.0;
	
	public static ChatColor colorMain = ChatColor.DARK_GREEN;
	public static ChatColor colorAccent = ChatColor.WHITE;
	public static ChatColor colorWarning = ChatColor.RED;
	
	//The folder where data is saved, starting path is the same location as craftbukkit.jar
	public static String dataFolder = "plugins/EmeraldExchange/";
	
	//Permitted items are specified in EmeraldExchange.java, in the PopulateMaterials function.
	
}
