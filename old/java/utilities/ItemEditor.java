package me.nosta.cachecache.utilities;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;

public class ItemEditor {

	public static void setUnbreakable(ItemStack item) {
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setUnbreakable(true);
		itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		item.setItemMeta(itemMeta);
	}

	public static void setLore(ItemStack item, String lore) {
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setLore(Collections.singletonList(lore));
		itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		item.setItemMeta(itemMeta);
	}

	public static void setLore(ItemStack item, ArrayList<String> lore) {
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setLore(lore);
		itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		item.setItemMeta(itemMeta);
	}

	public static void setDisplayName(ItemStack item, String displayName) {
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(ChatColor.RESET+displayName);
		item.setItemMeta(itemMeta);
	}

	public static void hideEnchants(ItemStack item) {
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(itemMeta);
	}
}
