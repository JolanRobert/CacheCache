package fr.cachecache.game;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.cachecache.ItemEditor;
import fr.cachecache.Role;
import net.md_5.bungee.api.ChatColor;

public class GameInventories {
	
	public static GameInventories instance;
	
	private Inventory configInventory;
	
	public void OnEnable() {
		instance = this;
		this.createConfigInventory();
	}	
	
	public void createConfigInventory() {
		this.configInventory = Bukkit.createInventory(null, 27, ChatColor.AQUA+""+ChatColor.BOLD+"Configuration");
		
		ItemStack item = new ItemStack(Material.LIME_TERRACOTTA);
		
		int index = 0;
		for (Role role : Role.values()) {
			ItemEditor.setDisplayName(item, role.name());
			ItemEditor.setLore(item, ChatColor.GREEN+""+ChatColor.BOLD+"ON");
			this.configInventory.setItem(index, item);
			index++;
		}		
		
		ItemStack close = new ItemStack(Material.BARRIER);
		ItemEditor.setDisplayName(close, ChatColor.RED+""+ChatColor.BOLD+"Fermer");
		this.configInventory.setItem(26, item);		
	}
	
	
	public Inventory getConfigInventory() {return this.configInventory;}
}
