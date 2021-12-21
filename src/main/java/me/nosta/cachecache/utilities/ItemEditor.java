package me.nosta.cachecache.utilities;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class ItemEditor {

	public static void setUnbreakable(ItemStack item) {
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setUnbreakable(true);
		item.setItemMeta(itemMeta);
	}
	
	public static void setLore(ItemStack item, String lore) {		
		ItemMeta itemMeta = item.getItemMeta();		
		itemMeta.setLore(Arrays.asList(lore));
		item.setItemMeta(itemMeta);
	}
	
	public static void setLore(ItemStack item, ArrayList<String> lore) {		
		ItemMeta itemMeta = item.getItemMeta();		
		itemMeta.setLore(lore);
		item.setItemMeta(itemMeta);
	}

	public static void setDisplayName(ItemStack item, String displayName) {
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(ChatColor.RESET+displayName);
		item.setItemMeta(itemMeta);
	}
}
