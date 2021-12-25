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

        PlayerRole pr = RoleManager.getInstance().getPlayerRoleWithPlayer(player);
        if (pr == null) return;

        RoleEnum role = pr.getRole() == RoleEnum.ESPION ? pr.getCover() : pr.getRole();

        if (role == RoleEnum.CAPITAINE) {
            if (hasCorrectItem(pr.getPlayer(),Material.NETHER_STAR,"Ralliement")) {
                event.setCancelled(true);
                PowerManager.getInstance().triggerPowerCapitaine(pr);
            }
        }

        else if (role == RoleEnum.NINJA) {
            if (hasCorrectItem(pr.getPlayer(),Material.NETHER_STAR,"Camouflage")) {
                event.setCancelled(true);
                PowerManager.getInstance().triggerPowerNinja(pr);
            }
        }
    }

    public static boolean hasCorrectItem(Player player, Material material, String itemName) {
        ItemStack mainHand = player.getInventory().getItemInMainHand();
        ItemStack offHand = player.getInventory().getItemInOffHand();

        if (mainHand.getType() == material) {
            if (mainHand.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.YELLOW+itemName)) return true;
        }

        if (offHand.getType() == material) {
            if (offHand.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.YELLOW+itemName)) return true;
        }

        return false;
    }
}
