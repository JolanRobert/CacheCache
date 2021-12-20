package fr.cachecache.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import fr.cachecache.game.GameInventories;

public class InventoryListener implements Listener {
	
	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent event) {
		Player player = (Player)event.getWhoClicked();
		Material clickedItem = event.getClickedInventory().getItem(event.getSlot()).getType();
		if (clickedItem == null) return;
		if (clickedItem == Material.BARRIER) player.closeInventory();
		
		if (event.getInventory() == GameInventories.instance.getConfigInventory()) {			
			switch (clickedItem) {
			case LIME_TERRACOTTA:
				break;
			case RED_TERRACOTTA:
				break;
			default: return;
			}
		}		
	}
}
