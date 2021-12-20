package cachecache.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import cachecache.game.GameInventories;

public class InventoryListener implements Listener {
	
	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent event) {
		Player player = (Player)event.getWhoClicked();
		ItemStack clickedItem = event.getClickedInventory().getItem(event.getSlot());
		if (clickedItem == null) return;
		if (clickedItem.getType() == Material.BARRIER) player.closeInventory();
		if (event.getInventory() != player.getInventory()) event.setCancelled(true);
		
		if (event.getInventory() == GameInventories.getInstance().getConfigInventory()) {			
			switch (clickedItem.getType()) {
			case LIME_TERRACOTTA:
				GameInventories.getInstance().ChangeRoleState(event.getSlot(), true);
				break;
			case RED_TERRACOTTA:
				GameInventories.getInstance().ChangeRoleState(event.getSlot(), false);
				break;
			default: return;
			}
		}		
	}
}
