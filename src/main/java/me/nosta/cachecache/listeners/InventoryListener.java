package me.nosta.cachecache.listeners;

import me.nosta.cachecache.elements.PlayerRole;
import me.nosta.cachecache.enums.RoleEnum;
import me.nosta.cachecache.managers.InventoryManager;
import me.nosta.cachecache.managers.PowerManager;
import me.nosta.cachecache.managers.RoleManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener {
	
	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent event) {
		if (event.getClickedInventory().getItem(event.getSlot()) == null) return;
		if (event.getClick() != ClickType.LEFT && event.getClick() != ClickType.RIGHT) return;

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

		//Player Inventory
		else if (event.getInventory() == player.getInventory()) {
			if (clickedItem.getType() != Material.NETHER_STAR) return;

			PlayerRole pr = RoleManager.getInstance().getPlayerRoleWithPlayer(player);
			if (pr == null) return;

			if (pr.getRole() == RoleEnum.CAPITAINE) {
				if (clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("Ralliement")) PowerManager.getInstance().triggerPowerCapitaine(pr);
			}

			else if (pr.getRole() == RoleEnum.NINJA) {
				if (clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("Camouflage")) PowerManager.getInstance().triggerPowerNinja(pr);
			}
		}
	}
}
