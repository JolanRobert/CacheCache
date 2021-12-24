package me.nosta.cachecache.listeners;

import me.nosta.cachecache.elements.PlayerRole;
import me.nosta.cachecache.enums.RoleEnum;
import me.nosta.cachecache.managers.PowerManager;
import me.nosta.cachecache.managers.RoleManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        ItemStack mainHand = player.getInventory().getItemInMainHand();
        ItemStack offHand = player.getInventory().getItemInOffHand();

        if (mainHand.getType() != Material.NETHER_STAR && offHand.getType() != Material.NETHER_STAR) return;

        event.setCancelled(true);

        PlayerRole pr = RoleManager.getInstance().getPlayerRoleWithPlayer(player);
        if (pr == null) return;


        if (pr.getRole() == RoleEnum.CAPITAINE || pr.getCover() == RoleEnum.CAPITAINE) {
            if (hasCorrectItem(mainHand,offHand,"Ralliement")) {
                PowerManager.getInstance().triggerPowerCapitaine(pr);
            }
        }

        else if (pr.getRole() == RoleEnum.NINJA || pr.getCover() == RoleEnum.NINJA) {
            if (hasCorrectItem(mainHand,offHand,"Camouflage")) {
                PowerManager.getInstance().triggerPowerNinja(pr);
            }
        }
    }

    public boolean hasCorrectItem(ItemStack mainHand, ItemStack offHand, String itemName) {
        if (mainHand.getType() == Material.NETHER_STAR) {
            System.out.println(mainHand.getItemMeta().getDisplayName());
            if (mainHand.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.YELLOW+itemName)) return true;
        }

        if (offHand.getType() == Material.NETHER_STAR) {
            System.out.println(offHand.getItemMeta().getDisplayName());
            if (offHand.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.YELLOW+itemName)) return true;
        }

        return false;
    }
}
