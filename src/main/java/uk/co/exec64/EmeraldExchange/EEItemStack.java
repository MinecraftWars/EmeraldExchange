package uk.co.exec64.EmeraldExchange;

import java.io.Serializable;

import org.bukkit.Material;

//I was driven to reinvent the wheel because
//ItemStack is only serializable through some
//silly config interface I just want raw
//dumping/reading to/from disk.
public class EEItemStack implements Serializable {

	private static final long serialVersionUID = -9066450876362521585L;
	public int quantity;
	public Material material;
	
	public EEItemStack() {
		this.quantity = 0;
		this.material = Material.AIR;
	}
	
	public EEItemStack(Material material, int quantity) {
		this.material = material;
		this.quantity = quantity;
	}
}
