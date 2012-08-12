package uk.co.exec64.EmeraldExchange;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class EEEventListener implements Listener {
	
	private EmeraldExchange plugin;
	
	EEEventListener(EmeraldExchange plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		String name = event.getPlayer().getName();
		
		//Send any notifications to the player
		plugin.notifyPlayer(name);
	}
	
}
