package me.nosta.cachecache.listeners;

import me.nosta.cachecache.managers.InventoryManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener {
	
	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent event) {
		if (event.getClickedInventory() == null) return;
		if (event.getClickedInventory().getItem(event.getSlot()) == null) return;
		Player player = (Player)event.getWhoClicked();
		ItemStack clickedItem = event.getClickedInventory().getItem(event.getSlot());

		//Configuration Inventory
		if (event.getInventory() == InventoryManager.getInstance().getConfigInventory()) {
			switch (clickedItem.getType()) {
				case LIME_TERRACOTTA:
					InventoryManager.getInstance().ChangeRoleState(event.getSlot(), true);
					break;
				case RED_TERRACOTTA:
					InventoryManager.getInstance().ChangeRoleState(event.getSlot(), false);
					break;
				case BARRIER:
					player.closeInventory();
					break;
				default: return;
			}
			event.setCancelled(true);
		}
	}
}
