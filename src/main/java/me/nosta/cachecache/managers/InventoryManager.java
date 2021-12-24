package me.nosta.cachecache.managers;

import me.nosta.cachecache.enums.RoleEnum;
import me.nosta.cachecache.utilities.ItemEditor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class InventoryManager {
	
	private static InventoryManager instance;
	
	private Inventory configInventory;

	public static InventoryManager getInstance() {
		if (instance == null) instance = new InventoryManager();
		return instance;
	}
	
	public InventoryManager() {
		this.createConfigInventory();
	}
	
	public void createConfigInventory() {
		this.configInventory = Bukkit.createInventory(null, 27, ChatColor.BLACK+""+ChatColor.BOLD+"Configuration");
		
		ItemStack item = new ItemStack(Material.LIME_TERRACOTTA);
		ItemEditor.setDisplayName(item, "Chasseur");
		ItemEditor.setLore(item, ChatColor.GREEN+""+ChatColor.BOLD+"ON"+ChatColor.GOLD+" [LOCK]");
		this.configInventory.setItem(0, item);

		this.configInventory.setItem(1, item);
		UpdateNbCivil();
		
		item.setType(Material.RED_TERRACOTTA);
		
		int index = 9;
		for (RoleEnum role : Arrays.copyOfRange(RoleEnum.values(), 2, RoleEnum.values().length)) {
			ItemEditor.setDisplayName(item, role.getName());
			ItemEditor.setLore(item, ChatColor.RED+""+ChatColor.BOLD+"OFF");			
			this.configInventory.setItem(index, item);
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
			RoleManager.getInstance().removeRole(RoleEnum.values()[slot-7]);
		}
		else {
			is.setType(Material.LIME_TERRACOTTA);
			ItemEditor.setLore(is, ChatColor.GREEN+""+ChatColor.BOLD+"ON");
			RoleManager.getInstance().addRole(RoleEnum.values()[slot-7]);
		}

		UpdateNbCivil();
	}

	public void UpdateNbCivil() {
		int nbCivils = RoleManager.getInstance().getPlayerRoles().size()-RoleManager.getInstance().getNbNonCivils();
		if (nbCivils < 0) nbCivils = 0;
		if (nbCivils == 0) this.configInventory.getContents()[1].setType(Material.RED_TERRACOTTA);
		else this.configInventory.getContents()[1].setType(Material.LIME_TERRACOTTA);
		ItemEditor.setDisplayName(this.configInventory.getContents()[1], "Civil ("+nbCivils+")");
	}
	
	public Inventory getConfigInventory() {return this.configInventory;}
}
