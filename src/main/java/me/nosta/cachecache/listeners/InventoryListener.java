package me.nosta.cachecache.listeners;

import me.nosta.cachecache.game.GameInventories;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryListener implements Listener {
	
	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent event) {
		Player player = (Player)event.getWhoClicked();
		Material clickedItem = event.getClickedInventory().getItem(event.getSlot()).getType();
		if (clickedItem == null) return;
		if (clickedItem == Material.BARRIER) player.closeInventory();
		
		if (event.getInventory() == GameInventories.getInstance().getConfigInventory()) {			
			switch (clickedItem) {
			case LIME_TERRACOTTA:
				GameInventories.getInstance().ChangeRoleState(event.getSlot(), true);
				break;
			case RED_TERRACOTTA:
				GameInventories.getInstance().ChangeRoleState(event.getSlot(), false);
				break;
			default: return;
			}
			event.setCancelled(true);
		}
	}
}
