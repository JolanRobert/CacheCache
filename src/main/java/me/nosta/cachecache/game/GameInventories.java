package me.nosta.cachecache.game;

import me.nosta.cachecache.elements.Role;
import me.nosta.cachecache.utilities.ItemEditor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class GameInventories {
	
	private static GameInventories instance;
	
	private Inventory configInventory;
	private Map<Role,Boolean> roles;

	public static GameInventories getInstance() {
		if (instance == null) instance = new GameInventories();
		return instance;
	}
	
	public GameInventories() {
		roles = new HashMap<Role,Boolean>();
		this.createConfigInventory();
	}
	
	public void createConfigInventory() {
		this.configInventory = Bukkit.createInventory(null, 27, ChatColor.BLACK+""+ChatColor.BOLD+"Configuration");
		
		ItemStack item = new ItemStack(Material.LIME_TERRACOTTA);
		ItemEditor.setDisplayName(item, "Chasseur");
		ItemEditor.setLore(item, ChatColor.GREEN+""+ChatColor.BOLD+"ON"+ChatColor.GOLD+" [LOCK]");
		this.configInventory.setItem(0, item);
		
		ItemEditor.setDisplayName(item, "Civil ("+Bukkit.getOnlinePlayers().size()+")");
		ItemEditor.setLore(item, ChatColor.GREEN+""+ChatColor.BOLD+"ON"+ChatColor.GOLD+" [LOCK]");
		this.configInventory.setItem(1, item);
		
		item.setType(Material.RED_TERRACOTTA);
		
		int index = 9;
		for (Role role : Arrays.copyOfRange(Role.values(), 2, Role.values().length)) {
			ItemEditor.setDisplayName(item, role.getName());
			ItemEditor.setLore(item, ChatColor.RED+""+ChatColor.BOLD+"OFF");			
			this.configInventory.setItem(index, item);
			roles.put(role, false);
			index++;			
		}		
		
		ItemStack close = new ItemStack(Material.BARRIER);
		ItemEditor.setDisplayName(close, ChatColor.RED+""+ChatColor.BOLD+"Fermer");
		this.configInventory.setItem(26, close);
	}
	
	public void ChangeRoleState(int slot, boolean isActive) {
		if (slot < 9) return;
		
		ItemStack is = this.configInventory.getContents()[slot];
		if (isActive) {
			is.setType(Material.RED_TERRACOTTA);
			ItemEditor.setLore(is, ChatColor.RED+""+ChatColor.BOLD+"OFF");
			roles.replace(Role.values()[slot-7], false);
		}
		else {
			is.setType(Material.LIME_TERRACOTTA);
			ItemEditor.setLore(is, ChatColor.GREEN+""+ChatColor.BOLD+"ON");	
			roles.replace(Role.values()[slot-7], true);
		}
		
		int nbCivils = Math.max(0, Math.min(Bukkit.getOnlinePlayers().size(), Bukkit.getOnlinePlayers().size() - (roles.keySet().stream().filter(role -> roles.get(role) == true).collect(Collectors.toSet()).size())));
		ItemEditor.setDisplayName(this.configInventory.getContents()[1], "Civil ("+nbCivils+")");
		
		//PrintRoles();
	}
	
	public void PrintRoles() {
		for (Role role : roles.keySet()) {
			System.out.println(role.getName()+" : "+roles.get(role));
		}
	}
	
	public Inventory getConfigInventory() {return this.configInventory;}
}
