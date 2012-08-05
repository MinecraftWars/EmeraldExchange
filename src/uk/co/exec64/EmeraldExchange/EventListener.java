package uk.co.exec64.EmeraldExchange;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class EventListener implements Listener {
	
	private EmeraldExchange plugin;
	
	EventListener(EmeraldExchange plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		String name = event.getPlayer().getName();
		
		//Send any notifications to the player
		plugin.notifyPlayer(name);
	}
	
}
